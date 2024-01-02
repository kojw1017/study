function catchTicket() {
    const MAX_BUY_SEAT = 10; // Define this value
    let mainDocu = window.parent.parent.parent.parent.document.querySelector('#ifrmSeat');
    let isComplete = false;
    function gogo() {
        if (isComplete) return;
        let seat_elements = [];

        let seatFrame = mainDocu.contentWindow.document.querySelector('#ifrmSeatDetail').contentWindow;
        let main_map = seatFrame.document.querySelector('#MainMap');
        let seat_selector = main_map ? ".stySeat" : ".SeatN";
        let sty_seats = seatFrame.document.querySelectorAll(seat_selector);

        let consecutive_seats = [];
        let count = 0;

        for (let seat of sty_seats) {
            if (seat.classList.contains(seat_selector.slice(1))) {
                consecutive_seats.push(seat);
                count += 1;
                if (count === MAX_BUY_SEAT) {
                    seat_elements = consecutive_seats;
                    break;
                }
            } else {
                consecutive_seats = [];
                count = 0;
            }
        }

        if (!seat_elements.length && sty_seats.length) {
            seat_elements = [sty_seats[0]];
        }

        if (seat_elements.length === 0) {
            mainDocu.contentWindow.document.querySelector('#ifrmSeatDetail').contentWindow.location.reload();
        } else {
            for (let seat of seat_elements) {
                seat.click();
            }
            mainDocu.contentWindow.document.querySelector('#NextStepImage').parentElement.click();
            isComplete = true;
        }
    }
    if (!mainDocu) {
        console.log("음..")
    } else {
        console.log("다시")
        setInterval(() => gogo(), 1500);
    }
}

function initMain() {
    catchTicket()
}

initMain();