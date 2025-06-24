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
}
