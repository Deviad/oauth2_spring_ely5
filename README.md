## Generate jks (key store) file

keytool -genkeypair -alias jwt -keyalg RSA -dname "CN=jwt, L=Berlin, S=Berlin, C=DE" -keypass password -keystore jwt.jks -storepass password


## Get the public key

keytool -list -rfc --keystore jwt.jks | openssl x509 -inform pem -pubkey
