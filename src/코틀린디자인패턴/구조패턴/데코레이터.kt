package 코틀린디자인패턴.구조패턴

/**
 * 객체간 관계를 다루는 디자인 패턴
 *
 *
 */

// 컴포넌트 인터페이스
interface MessageService {
    fun sendMessage(message: String)
}

// 구체적인 컴포넌트
class BasicMessageService : MessageService {
    override fun sendMessage(message: String) {
        println("Sending message: $message")
    }
}

// 데코레이터 추상 클래스
abstract class MessageDecorator(protected val wrappedService: MessageService) : MessageService

// 암호화 데코레이터
class EncryptedMessageDecorator(service: MessageService) : MessageDecorator(service) {
    override fun sendMessage(message: String) {
        val encryptedMessage = encrypt(message)
        println("Encrypting message...")
        wrappedService.sendMessage(encryptedMessage)
    }

    private fun encrypt(message: String): String {
        // 암호화 로직
        return message.reversed()
    }
}

// 압축 데코레이터
class CompressedMessageDecorator(service: MessageService) : MessageDecorator(service) {
    override fun sendMessage(message: String) {
        val compressedMessage = compress(message)
        println("Compressing message...")
        wrappedService.sendMessage(compressedMessage)
    }

    private fun compress(message: String): String {
        // 압축 로직
        return message.replace(" ", "")
    }
}

// 로깅 데코레이터
class LoggingMessageDecorator(service: MessageService) : MessageDecorator(service) {
    override fun sendMessage(message: String) {
        println("Logging: Sending message '$message'")
        wrappedService.sendMessage(message)
    }
}

fun main() {
    var service: MessageService = BasicMessageService()
    service = LoggingMessageDecorator(service)
    service = EncryptedMessageDecorator(service)
    service = CompressedMessageDecorator(service)

    service.sendMessage("Hello, World! This is a test message.")
}
