package pivot.app.test

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<TestApplication>().with(TestcontainersConfiguration::class).run(*args)
}
