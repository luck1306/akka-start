package com.example.akkaquote.actor

import akka.testkit.TestKit
import akka.actor.ActorSystem
import akka.testkit.ImplicitSender
import org.scalatest.matchers.should.Matchers
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.BeforeAndAfterAll
import akka.actor.Props
import akka.testkit.TestActorRef
import com.example.akkaquote.message.Quote
import com.example.akkaquote.message.QuoteAdded
import com.example.akkaquote.message.AddQuote

class QuotesHandlerActorTests
extends TestKit(ActorSystem("Tests")) // Help to perform more easier Akka Actors's Unit Test
with ImplicitSender // Test Class ensure receiveing Message as Actor Response
with Matchers with AnyFlatSpecLike // more nature Unit Test
with BeforeAndAfterAll // can override afterAll() Method for when Test is ended, prevent Memory Leak
{
    override def afterAll(): Unit = system.terminate() // 'system' Field is received By TestKit Class
    "A QuotesHandlerActor" should "add new quotes" in {
        val quoteHandlerActorRef = TestActorRef(Props[QuotesHandlerActor]())
        val actorInstance = quoteHandlerActorRef
        .underlyingActor.asInstanceOf[QuotesHandlerActor]

        actorInstance.quotes.size should be(0)

        val quote = Quote("This is a test", "me")
        quoteHandlerActorRef ! AddQuote(quote)
        expectMsg(QuoteAdded)

        actorInstance.quotes.size should be(1)
        actorInstance.quotes(0).quote should be("This is a test")
        actorInstance.quotes(0).author should be("me")
    }
}
