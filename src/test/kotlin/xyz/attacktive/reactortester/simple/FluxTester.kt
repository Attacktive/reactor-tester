package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class FluxTester {
	@Test
	fun `test priority - map vs switchIfEmpty`() {
		val flux = Flux.empty<String>()
			.map { it.uppercase() }
			.switchIfEmpty(Flux.just("empty"))

		StepVerifier.create(flux)
			.expectNext("empty")
			.verifyComplete()
	}

	@Test
	fun `test priority - map vs switchIfEmpty when not empty`() {
		val flux = Flux.just("foo", "bar", "baz")
			.map { it.uppercase() }
			.switchIfEmpty(Flux.just("empty"))

		StepVerifier.create(flux)
			.expectNext("FOO")
			.expectNext("BAR")
			.expectNext("BAZ")
			.verifyComplete()
	}
}
