package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class MonoTester {
	@Test
	fun `test priority - map vs switchIfEmpty`() {
		val mono = Mono.empty<String>()
			.map { "transformed" }
			.switchIfEmpty(Mono.just("empty"))

		StepVerifier.create(mono)
			.expectNext("empty")
			.verifyComplete()
	}

	@Test
	fun `test priority - flatMap vs switchIfEmpty`() {
		val mono = Mono.empty<String>()
			.flatMap { Mono.just("transformed") }
			.switchIfEmpty(Mono.just("empty"))

		StepVerifier.create(mono)
			.expectNext("empty")
			.verifyComplete()
	}

	@Test
	fun `test priority - map vs switchIfEmpty when not empty`() {
		val mono = Mono.just("something")
			.map { "transformed" }
			.switchIfEmpty(Mono.just("empty"))

		StepVerifier.create(mono)
			.expectNext("transformed")
			.verifyComplete()
	}

	@Test
	fun `test then on empty Mono`() {
		val mono = Mono.empty<String>()
			.then(Mono.just("fallback"))

		StepVerifier.create(mono)
			.expectNext("fallback")
			.verifyComplete()
	}

	@Test
	fun `test then vs switchIfEmpty on empty Mono`() {
		val thenMono = Mono.empty<String>()
			.then(Mono.just("then result"))

		val switchMono = Mono.empty<String>()
			.switchIfEmpty(Mono.just("switch result"))

		StepVerifier.create(thenMono)
			.expectNext("then result")
			.verifyComplete()

		StepVerifier.create(switchMono)
			.expectNext("switch result")
			.verifyComplete()
	}

	@Test
	fun `test then with map on empty Mono`() {
		val mono = Mono.empty<String>()
			.map { "this won't execute" }
			.then(Mono.just("then result"))

		StepVerifier.create(mono)
			.expectNext("then result")
			.verifyComplete()
	}
}
