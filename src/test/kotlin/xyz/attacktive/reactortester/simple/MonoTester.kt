package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoTester {
	@Test
	fun `test priority - map vs switchIfEmpty`() {
		val mono = Mono.empty<String>()
			.map { "transformed" }
			.switchIfEmpty(Mono.just("empty fallback"))

		StepVerifier.create(mono)
			.expectNext("empty fallback")
			.verifyComplete()
	}

	@Test
	fun `test priority - flatMap vs switchIfEmpty`() {
		val mono = Mono.empty<String>()
			.flatMap { Mono.just("transformed") }
			.switchIfEmpty(Mono.just("flatMap empty fallback"))

		StepVerifier.create(mono)
			.expectNext("flatMap empty fallback")
			.verifyComplete()
	}

	@Test
	fun `test priority - map vs switchIfEmpty when not empty`() {
		val mono = Mono.just("something")
			.map { "transformed value" }
			.switchIfEmpty(Mono.just("empty"))

		StepVerifier.create(mono)
			.expectNext("transformed value")
			.verifyComplete()
	}

	@Test
	fun `test then on empty Mono`() {
		val mono = Mono.empty<String>()
			.then(Mono.just("then fallback value"))

		StepVerifier.create(mono)
			.expectNext("then fallback value")
			.verifyComplete()
	}

	@Test
	fun `test then vs switchIfEmpty on empty Mono`() {
		val thenMono = Mono.empty<String>()
			.then(Mono.just("then operator output"))

		val switchMono = Mono.empty<String>()
			.switchIfEmpty(Mono.just("switchIfEmpty operator output"))

		StepVerifier.create(thenMono)
			.expectNext("then operator output")
			.verifyComplete()

		StepVerifier.create(switchMono)
			.expectNext("switchIfEmpty operator output")
			.verifyComplete()
	}

	@Test
	fun `test then with map on empty Mono`() {
		val mono = Mono.empty<String>()
			.map { "this won't execute" }
			.then(Mono.just("then after map output"))

		StepVerifier.create(mono)
			.expectNext("then after map output")
			.verifyComplete()
	}

	@Test
	fun `test then on error`() {
		val mono = Mono.error<String>(RuntimeException("intentionally thrown exception"))
			.thenReturn("ignored")

		StepVerifier.create(mono)
			.expectErrorSatisfies {
				it is RuntimeException
				it.message == "intentionally thrown exception"
			}
			.verify()
	}
}
