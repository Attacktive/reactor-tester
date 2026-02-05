package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import reactor.core.publisher.Mono
import reactor.test.StepVerifier

class EmptinessTester {
	@Test
	fun `verify map is ignored on empty`() {
		val mono = Mono.empty<Int>()
			.map { println(it) }

		StepVerifier.create(mono)
			.expectNextCount(0)
			.verifyComplete()
	}

	@Test
	fun `verify zip emits nothing when one mono is empty`() {
		val source1 = Mono.just("Data")
		val source2 = Mono.empty<Int>()

		val zippedMono = Mono.zip(source1, source2)

		StepVerifier.create(zippedMono)
			.expectNextCount(0)
			.verifyComplete()
	}

	@Test
	fun `defaultIfEmpty emits fallback when upstream is empty`() {
		val mono = Mono.empty<Int>()
			.defaultIfEmpty(1337)

		StepVerifier.create(mono)
			.expectNext(1337)
			.verifyComplete()
	}

	@Test
	fun `defaultIfEmpty does not override non-empty upstream`() {
		val mono = Mono.just(1)
			.defaultIfEmpty(1337)

		StepVerifier.create(mono)
			.expectNext(1)
			.verifyComplete()
	}

	@Test
	fun `switchIfEmpty switches to fallback mono when upstream is empty`() {
		val mono = Mono.empty<Int>()
			.switchIfEmpty(Mono.just(42))

		StepVerifier.create(mono)
			.expectNext(42)
			.verifyComplete()
	}

	@Test
	fun `switchIfEmpty does not switch when upstream is not empty`() {
		val mono = Mono.just(1)
			.switchIfEmpty(Mono.just(42))

		StepVerifier.create(mono)
			.expectNext(1)
			.verifyComplete()
	}

	@Test
	fun `zip still emits when one mono is empty but defaultIfEmpty is applied`() {
		val source1 = Mono.just("Data")
		val source2 = Mono.empty<Int>().defaultIfEmpty(-1)

		val zippedMono = Mono.zip(source1, source2)

		StepVerifier.create(zippedMono)
			.expectNextMatches { it.t1 == "Data" && it.t2 == -1 }
			.verifyComplete()
	}

	@Test
	fun `materialize turns empty into a value signal`() {
		val mono = Mono.empty<Int>()
			.materialize()

		StepVerifier.create(mono)
			.expectNextMatches { it.isOnComplete }
			.verifyComplete()
	}
}
