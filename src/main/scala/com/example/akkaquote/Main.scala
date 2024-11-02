package com.example.akkaquote

import akka.actor.{ ActorSystem, Props, ActorRef }
import com.example.akkaquote.actor.{ QuotesHandlerActor, QuotePrinterActor }
import com.example.akkaquote.message.{ Quote, AddQuote, RequestQuote, PrintRandomQuote }

import akka.pattern.ask // using ask pattern & using overloaded operator '?'
import scala.concurrent.Await // wating for End of Future Object's Processing

// for simple TimeOut's Args
import akka.util.Timeout
import scala.concurrent.duration._ // Before scala.concurrent.duration.DurationInt
import scala.language.postfixOps

object Main extends App{
    val system = ActorSystem("AkkaQuote")
    import system.dispatcher // Import ExecutionContext for Futures
    val quoteActorRef = system.actorOf(Props(new QuotesHandlerActor()), "quotesActor")
    val quotePrinterActorRef = system.actorOf(Props(new QuotePrinterActor(quoteActorRef)), "quotesPrinterActor")

    implicit val timeout: Timeout = Timeout(10.seconds)
    val future1 = quoteActorRef ? AddQuote(new Quote("Hello World", "Vairous book authors"))
    val future2 = quoteActorRef ? AddQuote(new Quote("To be or not to be", "W. Shakespeare"))
    val future3 = quoteActorRef ? AddQuote(new Quote("In The middle of difficulty lies opportunity", "A. Einstein"))
    
    Await.result(future1, timeout.duration)
    Await.result(future2, timeout.duration)
    Await.result(future3, timeout.duration)
    
    val future4 = quotePrinterActorRef ? PrintRandomQuote
    // Await.result(future4, timeout.duration)
    

    system.terminate()
}
