package com.example.akkaquote.actor

import akka.actor.Actor
import scala.collection.mutable.ListBuffer
import com.example.akkaquote.message.Quote
import scala.util.Random
import com.example.akkaquote.message.{ Quote, AddQuote, QuoteAdded, RequestQuote, QuoteRequested }

class QuotesHandlerActor extends Actor{
    val quotes = ListBuffer.empty[Quote]
    override def receive: Actor.Receive = {
        case AddQuote(quote) => {
            quotes += quote
            sender() ! QuoteAdded
        }
        case RequestQuote(originalSender) => {
            val index = Random.nextInt(quotes.size)
            sender() ! QuoteRequested(quotes(index), originalSender)
        }
    }
}
