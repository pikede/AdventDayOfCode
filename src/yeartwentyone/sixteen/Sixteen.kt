package yeartwentyone.sixteen

import java.math.BigInteger
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val input = Files.readAllLines(Paths.get("src/yeartwentyone/sixteen/file.txt")) as ArrayList

    println(Decoder(input))
}

class Decoder(packetInput: ArrayList<String>) {

    private var packet = StringBuilder()

    init {

        val binaryMap = hashMapOf<String, String>()

        binaryMap["0"] = "0000"
        binaryMap["1"] = "0001"
        binaryMap["2"] = "0010"
        binaryMap["3"] = "0011"
        binaryMap["4"] = "0100"
        binaryMap["5"] = "0101"
        binaryMap["6"] = "0110"
        binaryMap["7"] = "0111"
        binaryMap["8"] = "1000"
        binaryMap["9"] = "1001"
        binaryMap["A"] = "1010"
        binaryMap["B"] = "1011"
        binaryMap["C"] = "1100"
        binaryMap["D"] = "1101"
        binaryMap["E"] = "1110"
        binaryMap["F"] = "1111"

        packetInput[0].forEach {
            packet.append(binaryMap["" + it])
        }

        partOne()  // 996
        partTwo()  // 96257984154
    }

    fun partOne() {
        val parser = Parser.getParser(packet.toString())
        parser.parse()
        println(parser.getVersionSum())
    }

    fun partTwo() {
        val parser = Parser.getParser(packet.toString())
        parser.parse()
        println(parser.getParseValue())
    }

}

abstract class Parser {
    open var version = 0
    open var typeId = 0
    open var packet = ""
    open var value: BigInteger = BigInteger.ZERO
    open var size = 0

    companion object {
        fun getParser(input: String): Parser {
            var version = BigInteger(input.substring(0, 3), 2).toInt()
            var typeId = BigInteger(input.substring(3, 6), 2).toInt()

            return if (typeId == 4) {
                // literal value
                Value(input.substring(6), version, typeId, 6)
            } else {
                Operator(input.substring(7), version, typeId, input[6] == '1', 7)
            }
        }
    }

    abstract fun parse()
    abstract fun getVersionSum(): Int
    abstract fun getParseValue(): BigInteger
}

class Operator() : Parser() {
    private var listOfOperatorPackets = ArrayList<Parser>()
    private var hasSubPacket = false

    constructor(data: String, version: Int, type: Int, subPackages: Boolean, size: Int) : this() {
        this.version = version
        this.typeId = type
        this.packet = data
        this.size = size
        this.hasSubPacket = subPackages
    }

    override fun parse() {
        if (hasSubPacket) { //->11 number of subPackets
            val numberOfPackets = BigInteger(this.packet.substring(0, 11), 2).toInt()
            var subPacket = this.packet.substring(11)
            this.size += 11
            for (i in 0 until numberOfPackets) {
                val packetVersion = getParser(subPacket)
                packetVersion.parse()
                listOfOperatorPackets.add(packetVersion)
                this.size += packetVersion.size
                subPacket = subPacket.substring(packetVersion.size)
            }
        } else { //-> 15 length of subPackets
            val length = BigInteger(this.packet.substring(0, 15), 2).toInt()
            var subPacket = this.packet.substring(15, length + 15)
            this.size += 15

            while (subPacket.isNotBlank()) {
                val packetVersion = getParser(subPacket)
                packetVersion.parse()
                listOfOperatorPackets.add(packetVersion)
                this.size += packetVersion.size
                subPacket = subPacket.substring(packetVersion.size)
            }
        }
    }

    override fun getVersionSum(): Int {
        var sum = version

        listOfOperatorPackets.forEach { sum += it.getVersionSum() }

        return sum
    }

    override fun getParseValue(): BigInteger {
        var sum = BigInteger.ZERO

        when (typeId) {
            0 -> listOfOperatorPackets.forEach { sum = sum.add(it.getParseValue()) }
            1 -> {
                sum = BigInteger.ONE
                listOfOperatorPackets.forEach { sum = sum.multiply(it.getParseValue()) }
            }
            2 -> {
                sum = listOfOperatorPackets[0].getParseValue()
                listOfOperatorPackets.forEach { sum = minOf(sum, it.getParseValue()) }
            }
            3 -> {
                sum = listOfOperatorPackets[0].getParseValue()
                listOfOperatorPackets.forEach { sum = maxOf(sum, it.getParseValue()) }
            }
            5 -> sum =
                if (listOfOperatorPackets[0].getParseValue() > listOfOperatorPackets[1].getParseValue()) BigInteger.ONE else BigInteger.ZERO
            6 -> sum =
                if (listOfOperatorPackets[0].getParseValue() < listOfOperatorPackets[1].getParseValue()) BigInteger.ONE else BigInteger.ZERO
            7 -> sum =
                if (listOfOperatorPackets[0].getParseValue() == listOfOperatorPackets[1].getParseValue()) BigInteger.ONE else BigInteger.ZERO
        }

        return sum
    }

}

class Value() : Parser() {

    constructor(data: String, version: Int, type: Int, size: Int) : this() {
        this.version = version
        this.typeId = type
        this.packet = data
        this.size = size
    }

    private var parseValue = BigInteger.ZERO

    override fun parse() {
        val packetLiteralValue = StringBuilder()

        while (this.packet.isNotBlank()) {
            packetLiteralValue.append(this.packet.substring(1, 5))
            this.size += 5

            if (packet[0] == '0') {
                break
            }

            packet = packet.substring(5)
        }

        this.value = BigInteger(packetLiteralValue.toString(), 2)
        this.parseValue = BigInteger(packetLiteralValue.toString(), 2)
    }

    override fun getVersionSum() = version

    override fun getParseValue(): BigInteger = value
}
