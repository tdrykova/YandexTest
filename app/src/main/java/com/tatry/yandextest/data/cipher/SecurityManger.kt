package com.tatry.yandextest.data.cipher

import android.content.Context
import android.os.Environment
import android.util.Base64
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.security.MessageDigest
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

class SecurityManger(private val context: Context) {

    companion object{
        private const val KEYPASSWORD = "RemoteControl123"
        private const val IVPASSWORD = "123ControlRemote"

        private const val ALGORITHM = "AES"
        private const val TRANSFORMATION = "AES/CBC/PKCS7Padding"
    }


    private val secretKeySpec = generateKey()

    private fun generateKey() : SecretKeySpec {
        val digest = MessageDigest.getInstance("SHA-256")
        val bytes = KEYPASSWORD.toByteArray()
        digest.update(bytes,0,bytes.size)
        return SecretKeySpec(digest.digest(), ALGORITHM)
    }

    fun encryptFile(fileName: String, encryptFileName:String){
        val readFileToString = File(fileName).readText()

        val encryptFile = File(context.filesDir,encryptFileName)
        if (encryptFile.exists()){
            encryptFile.delete()
        }
        encryptFile.createNewFile()

        val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(
                Cipher.ENCRYPT_MODE,
                secretKeySpec,
                IvParameterSpec(IVPASSWORD.toByteArray())
            )
        }
        val fos = FileOutputStream(encryptFile)
        val cos = CipherOutputStream(fos,encryptCipher)
        cos.write(readFileToString.encodeToByteArray())
        cos.flush()
        cos.close()
        Log.d("status","Encryption File Successfully")
    }

    fun encryptFile(inputStream: InputStream, encryptFileName: String) {
        val encryptFile = File(context.filesDir, encryptFileName)
        if (encryptFile.exists()) {
            encryptFile.delete()
        }
        encryptFile.createNewFile()

        val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(
                Cipher.ENCRYPT_MODE,
                secretKeySpec,
                IvParameterSpec(IVPASSWORD.toByteArray())
            )
        }
        val fos = FileOutputStream(encryptFile)
        val cos = CipherOutputStream(fos, encryptCipher)

        inputStream.copyTo(cos)

        cos.flush()
        cos.close()
        Log.d("status", "Encryption File Successfully")
    }

    fun encrypt(strToEncrypt: String) :  String?
    {
        try
        {
//            val ivParameterSpec = IvParameterSpec(Base64.decode(AESEncyption.iv, Base64.DEFAULT))

//            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
//            val spec =  PBEKeySpec(AESEncyption.secretKey.toCharArray(), Base64.decode(AESEncyption.salt, Base64.DEFAULT), 10000, 256)
//            val tmp = factory.generateSecret(spec)
//            val secretKey =  SecretKeySpec(tmp.encoded, "AES")
//
//            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding")
//            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivParameterSpec)
            val encryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(
                    Cipher.ENCRYPT_MODE,
                    secretKeySpec,
                    IvParameterSpec(IVPASSWORD.toByteArray())
                )
            }

            return Base64.encodeToString(encryptCipher.doFinal(strToEncrypt.toByteArray(Charsets.UTF_8)), Base64.DEFAULT)
        }
        catch (e: Exception)
        {
            println("Error while encrypting: $e")
        }
        return null
    }


    fun decryptFile(encryptFileName : String) :String{

        // Load Encrypt File From Asset
//        val fis = context.assets.open(encryptFileName)


        val encryptedFile = File(context.filesDir,encryptFileName)
        val fis = FileInputStream(encryptedFile)


        val decryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE,secretKeySpec,IvParameterSpec(IVPASSWORD.toByteArray()))
        }

        val inputStream = CipherInputStream(fis,decryptCipher)

        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextBytes = inputStream.read()
        while (nextBytes != -1){
            byteArrayOutputStream.write(nextBytes)
            nextBytes = inputStream.read()
        }

        Log.d("status","Decryption File Successfully")
        Log.d("status","byteArrayOutputStream $byteArrayOutputStream")

        return byteArrayOutputStream.toByteArray().decodeToString().trim()
    }

    fun decrypt(strToDecrypt : String) : String? {
        try
        {

//            val ivParameterSpec =  IvParameterSpec(Base64.decode(iv, Base64.DEFAULT))
//
//            val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
//            val spec =  PBEKeySpec(secretKey.toCharArray(), Base64.decode(salt, Base64.DEFAULT), 10000, 256)
//            val tmp = factory.generateSecret(spec);
//            val secretKey =  SecretKeySpec(tmp.encoded, "AES")
//
//            val cipher = Cipher.getInstance("AES/CBC/PKCS7Padding");
//            cipher.init(Cipher.DECRYPT_MODE, secretKey, ivParameterSpec);
            val decryptCipher = Cipher.getInstance(TRANSFORMATION).apply {
                init(Cipher.DECRYPT_MODE,secretKeySpec,IvParameterSpec(IVPASSWORD.toByteArray()))
            }
            return  String(decryptCipher.doFinal(Base64.decode(strToDecrypt, Base64.DEFAULT)))
        }
        catch (e : Exception) {
            println("Error while decrypting: $e");
        }
        return null
    }

    private fun Context.loadFromAsset(fileName: String):String{
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val byteArray = ByteArray(size)
        inputStream.read(byteArray)
        inputStream.close()
        return String(byteArray,Charsets.UTF_8)
    }

}