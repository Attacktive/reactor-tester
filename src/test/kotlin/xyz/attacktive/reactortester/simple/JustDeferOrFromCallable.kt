package xyz.attacktive.reactortester.simple

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import reactor.core.publisher.Mono

class JustDeferOrFromCallable {
	@Test
	fun `just is lazy but not really`() {
		val clock = Mono.just(System.currentTimeMillis())

		Thread.sleep(1000)
		val t1 = clock.block()

		Thread.sleep(1000)
		val t2 = clock.block()

		assertEquals(t1, t2)
	}

	@Test
	fun `defer actually defers like a champ`() {
		val clock = Mono.defer { Mono.just(System.currentTimeMillis()) }

		Thread.sleep(1000)
		val t1 = clock.block()

		Thread.sleep(1000)
		val t2 = clock.block()

		assertNotEquals(t1, t2)
	}

	@Test
	fun `callable also defers because it's built different`() {
		val clock = Mono.fromCallable { System.currentTimeMillis() }

		Thread.sleep(1000)
		val t1 = clock.block()

		Thread.sleep(1000)
		val t2 = clock.block()

		assertNotEquals(t1, t2)
	}
}
