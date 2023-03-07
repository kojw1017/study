package 오브젝트2

//45p
class Audience(private var amount: Long){
    private var ticket: Ticket? = Ticket.EMPTY
    private var invitation = Invitation.EMPTY
    fun buyTicket(seller: TicketSeller){
        ticket = seller.getTicket(this)
    }
    fun hasAmount(amount: Long) = this.amount >= amount
    fun minusAmount(amount: Long): Boolean{
        if(amount > this.amount) return false
        this.amount -= amount
        return true
    }
    fun getInvitation() = invitation
    fun removeInvitation(){
        invitation = Invitation.EMPTY
    }
    fun getTicket() = ticket
    fun setInvitation(invitation: Invitation){
        this.invitation = invitation
    }
}
class Bag(private var amount: Long, private val invitation: Invitation? = null){
    private var ticket: Ticket? = null
    fun hasInvitation() = invitation != null
    fun hasTicket() = ticket != null
    val setTicket = { ticket:Ticket -> this.ticket = ticket }
    val minusAmount = { amount: Long -> this.amount -= amount }
    val plusAmount = { amount: Long -> this.amount += amount }
}
class Invitation(private val theater: Theater?){
    companion object{
        val EMPTY = Invitation(null)
    }

}
class Ticket(private val theater: Theater?){
    companion object{
        val EMPTY = Ticket(null)
    }
    private var isEntered = false
    fun isValid(theater: Theater) = !(isEntered || (theater != this.theater) || (this == EMPTY))
    fun getFee() = theater?.fee
}
class TicketSeller{
    private var ticketOffice: TicketOffice? = null
    fun setTicketOffice(ticketOffice: TicketOffice){
        this.ticketOffice = ticketOffice
    }
    fun getTicket(audience: Audience): Ticket?{
        var ticket:Ticket? = Ticket.EMPTY
        if(audience.getInvitation() != Invitation.EMPTY){
            ticket = ticketOffice?.getTicketWithNoFee()
            if(ticket != Ticket.EMPTY) audience.removeInvitation()
        }else if(ticketOffice?.let { it.getTicketPrice()?.let { it1 -> audience.hasAmount(it1) } } == true){
            ticket = ticketOffice?.getTicketWithFee()
            if(ticket != Ticket.EMPTY) ticketOffice?.getTicketPrice()?.let { audience.minusAmount(it) }
        }
        return ticket
    }
}
class TicketOffice(private var amount: Long){
    private val tickets = mutableListOf<Ticket>()

    val addTicket = {ticket: Ticket -> tickets.add(ticket)}
    fun getTicketWithFee(): Ticket {
        return if(tickets.isEmpty()) Ticket.EMPTY
        else{
            val ticket = tickets.removeAt(0)
            ticket.getFee()?.let{ amount += it }
            return ticket
        }
    }
    fun getTicketWithNoFee(): Ticket {
        return if(tickets.isEmpty()) Ticket.EMPTY
        else tickets.removeAt(0)
    }
    fun getTicketPrice(): Long? {
        return if(tickets.isEmpty()) 0L
        else tickets[0].getFee()
    }
}

class Theater(val fee: Long){
    private val ticketOffices = mutableListOf<TicketOffice>()
    fun setTicketOffices(vararg ticketOffice: TicketOffice){
        this.ticketOffices.addAll(ticketOffices)
    }
    fun setTicket(ticketOffice: TicketOffice, num: Long){
        if(ticketOffices.contains(ticketOffice)){
            var count = num
            while (count-- > 0){
                ticketOffice.addTicket(Ticket(this))
            }
        }
    }
    fun setInvitation(audience: Audience){
        audience.setInvitation(Invitation(this))
    }
    fun enter(audience: Audience): Boolean?{
        val ticket = audience.getTicket()
        return ticket?.isValid(this)
    }
}

fun main(){
    val theater = Theater(100L)
    val audience1 = Audience(0L)
    val audience2 = Audience(50L)
    val ticketOffice = TicketOffice(0L)
    val seller = TicketSeller()

    theater.setTicketOffices(ticketOffice)
    theater.setTicket(ticketOffice, 10L)
    theater.setInvitation(audience1)

    seller.setTicketOffice(ticketOffice)

    audience1.buyTicket(seller)
    audience2.buyTicket(seller)

    val isOk1 = theater.enter(audience1)
    val isOk2 = theater.enter(audience2)

    println(isOk1)
    println(isOk2)
}