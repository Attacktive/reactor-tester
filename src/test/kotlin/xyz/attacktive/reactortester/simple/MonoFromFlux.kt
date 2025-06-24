package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Flux
import reactor.test.StepVerifier

class MonoFromFlux {
	companion object {
		val flux = Flux.just("foo", "bar", "baz")
	}

	@Test
	fun `test next`() {
		StepVerifier.create(flux.next())
			.expectNext("foo")
			.verifyComplete()
	}

	@Test
	fun `test next next`() {
		StepVerifier.create(flux.skip(1).next())
			.expectNext("bar")
			.verifyComplete()
	}

	@Test
	fun `test last`() {
		StepVerifier.create(flux.last())
			.expectNext("baz")
			.verifyComplete()
	}

	@Test
	fun `test single`() {
		val flux = Flux.just("the only element")

		StepVerifier.create(flux.single())
			.expectNext("the only element")
			.verifyComplete()
	}

	@Test
	fun `test single with no elements`() {
		val flux = Flux.empty<String>()

		StepVerifier.create(flux.single())
			.expectError(NoSuchElementException::class.java)
			.verify()
	}

	@Test
	fun `test single with multiple elements`() {
		StepVerifier.create(flux.single())
			.expectError(IndexOutOfBoundsException::class.java)
			.verify()
	}

	@Test
	fun `test singleOrEmpty with no elements`() {
		val flux = Flux.empty<String>()

		StepVerifier.create(flux.singleOrEmpty())
			.expectNextCount(0)
			.verifyComplete()
	}

	@Test
	fun `test singleOrEmpty with multiple elements`() {
		StepVerifier.create(flux.singleOrEmpty())
			.expectError(IndexOutOfBoundsException::class.java)
			.verify()
	}

	@Test
	fun `test collectList`() {
		StepVerifier.create(flux.collectList())
			.expectNext(listOf("foo", "bar", "baz"))
			.verifyComplete()
	}

	@Test
	fun `test elementAt`() {
		StepVerifier.create(flux.elementAt(0))
			.expectNext("foo")
			.verifyComplete()

		StepVerifier.create(flux.elementAt(1))
			.expectNext("bar")
			.verifyComplete()

		StepVerifier.create(flux.elementAt(2))
			.expectNext("baz")
			.verifyComplete()
	}

	@Test
	fun `test reduce`() {
		StepVerifier.create(flux.reduce { left, right -> "$left $right" })
			.expectNext("foo bar baz")
			.verifyComplete()
	}
}
