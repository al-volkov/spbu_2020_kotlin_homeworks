package utils

fun String.changeFormat(): List<String> = this.replace("(", "").replace(")", "").split(", ")
