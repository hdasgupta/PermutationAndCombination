package org.combination.permutaion

class CountLessThanSelectException(val count:Int, val select: Int):
    RuntimeException("Count($count) must me greater than or equals to Select($select)")