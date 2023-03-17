package 오브젝트1

//45p
import java.time.LocalTime
class Audience(var bag: Bag)
class Bag(private var amount: Long, private val invitation: Invitation? = null){
    private var ticket: Ticket? = null
    fun hasInvitation() = invitation != null
    fun hasTicket() = ticket != null
    val setTicket = { ticket:Ticket -> this.ticket = ticket }
    val minusAmount = { amount: Long -> this.amount -= amount }
    val plusAmount = { amount: Long -> this.amount += amount }
}
class Invitation(val invitationDate: LocalTime)
class Ticket(var fee: Long)
class TicketSeller(private val ticketOffice: TicketOffice){
    fun getTicketOffice() = ticketOffice
}
class TicketOffice(private var amount: Long, private val tickets: MutableList<Ticket>){
    fun getTicket() = tickets.removeAt(0)
    val minusAmount = { amount: Long -> this.amount -= amount }
    val plusAmount = { amount: Long -> this.amount += amount }
}

class Theater(private val ticketSeller: TicketSeller){
    fun enter(audience: Audience){
        if(audience.bag.hasInvitation()){
            val ticket = ticketSeller.getTicketOffice().getTicket()
            audience.bag.setTicket(ticket)
        }else{
            val ticket = ticketSeller.getTicketOffice().getTicket()
            audience.bag.minusAmount(ticket.fee)
            ticketSeller.getTicketOffice().plusAmount(ticket.fee)
            audience.bag.setTicket(ticket)
        }
    }
}