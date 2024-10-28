package com.henry.onlineshopping.utility.constant

object NetworkConstant {
    // Change this constant based on your current IP address
    private const val IP_ADDRESS = "192.168.1.14"

    // HTTP
    private const val HTTP_PORT = 8080
    const val HTTP_URL = "http://$IP_ADDRESS:$HTTP_PORT/"

    // HTTPS
    private const val HTTPS_PORT = 8443
    const val HTTPS_URL = "https://$IP_ADDRESS:$HTTPS_PORT/"
}