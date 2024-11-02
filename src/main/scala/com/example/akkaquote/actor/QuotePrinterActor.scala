package com.example.akkaquote.actor

import akka.actor.{ Actor, ActorRef }
import com.example.akkaquote.message.{ PrintRandomQuote, RequestQuote, QuoteRequested, QuotePrinted }


class QuotePrinterActor(val quoteManagerActorRef: ActorRef) extends Actor {

  override def receive: Actor.Receive = {
    case PrintRandomQuote => {
        val originalSender = sender()
        quoteManagerActorRef ! RequestQuote(originalSender)
    }
    
    case QuoteRequested(quote, originalSender) => {
        println("\"%s\"".format(quote.quote))
        println("-- %s".format(quote.author))
        originalSender ! QuotePrinted
    }
  }
}
