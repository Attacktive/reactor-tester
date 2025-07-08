package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class FluxTester {
	@Test
	fun `test priority - map vs switchIfEmpty`() {
		val flux = Flux.empty<String>()
			.map { it.lowercase() }
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

	@Test
	fun `test then on empty Flux`() {
		val flux = Flux.empty<String>()
			.then(Mono.just("fallback value"))

		StepVerifier.create(flux)
			.expectNext("fallback value")
			.verifyComplete()
	}

	@Test
	fun `test then vs switchIfEmpty on empty Flux`() {
		val thenFlux = Flux.empty<String>()
			.then(Mono.just("then operator result"))

		val switchFlux = Flux.empty<String>()
			.switchIfEmpty(Flux.just("switchIfEmpty operator result"))

		StepVerifier.create(thenFlux)
			.expectNext("then operator result")
			.verifyComplete()

		StepVerifier.create(switchFlux)
			.expectNext("switchIfEmpty operator result")
			.verifyComplete()
	}

	@Test
	fun `test then with map on empty Flux`() {
		val flux = Flux.empty<String>()
			.map { "this won't execute" }
			.then(Mono.just("then after map result"))

		StepVerifier.create(flux)
			.expectNext("then after map result")
			.verifyComplete()
	}
}
