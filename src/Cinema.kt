package cinema

fun main() {

    val cinema = createCinema()

    showMenu(cinema)
}

fun createCinema(): MutableList<MutableList<Char>> {
    println("Enter the number of rows:")
    val numOfRows = readln().toInt()

    println("Enter the number of seats in each row:")
    val numOfSeats = readln().toInt()

    val cinema = mutableListOf<MutableList<Char>>()
    repeat(numOfRows) {
        val tempRow = mutableListOf<Char>()
        repeat(numOfSeats) { tempRow.add('S') }
        cinema.add(tempRow)
    }
    return cinema
}

fun showMenu(cinema: MutableList<MutableList<Char>>) {
    println()
    println("1. Show the seats")
    println("2. Buy a ticket")
    println("3. Statistics")
    println("0. Exit")
    when (readln().toInt()) {
        0 -> {
            // Do nothing
        }

        1 -> printCinema(cinema)
        2 -> bookTicket(cinema)
        3 -> showStatistics(cinema)
        else -> {
            println("Invalid input")
            showMenu(cinema)
        }
    }
}

fun printCinema(cinema: MutableList<MutableList<Char>>) {
    println()
    println("Cinema:")
    print(" ")
    repeat(cinema[0].size) { print(" ${it + 1}") }
    println()

    for (i in cinema.indices) {
        println("${i + 1} ${cinema[i].joinToString(" ")}")
    }

    showMenu(cinema)
}

fun bookTicket(cinema: MutableList<MutableList<Char>>) {
    println()

    println("Enter a row number:")
    val selectedRow = readln().toInt()

    println("Enter a seat number in that row:")
    val selectedSeat = readln().toInt()

    if (cinema[selectedRow - 1][selectedSeat - 1] == 'B') {
        println()
        println("That ticket has already been purchased!")
        bookTicket(cinema)
    } else {
        try {
            val newCinema = bookSeat(selectedRow, selectedSeat, cinema)
            println("Ticket price: $${calcTicketPrice(selectedRow, cinema)}")
            showMenu(newCinema)
        } catch (e: IndexOutOfBoundsException) {
            println()
            println("Wrong input!")
            bookTicket(cinema)
        }
    }
}

fun calcTicketPrice(rowNum: Int, cinema: MutableList<MutableList<Char>>): Int {
    return if (cinema.size * cinema[0].size <= 60) 10
    else if (rowNum <= cinema.size / 2) 10
    else 8

}

fun bookSeat(rowNum: Int, seatNum: Int, cinema: MutableList<MutableList<Char>>): MutableList<MutableList<Char>> {
    cinema[rowNum - 1][seatNum - 1] = 'B'
    return cinema
}

fun showStatistics(cinema: MutableList<MutableList<Char>>) {
    var numOfTicketsPurchased = 0
    var currentIncome = 0

    repeat(cinema.size) { row ->
        repeat(cinema[row].size) { seat ->
            if (cinema[row][seat] == 'B') {
                numOfTicketsPurchased++
                currentIncome += if (cinema.size * cinema[0].size > 60) {
                    if (row < cinema.size / 2) {
                        10
                    } else {
                        8
                    }
                } else {
                    10
                }
            }
        }
    }

    println()
    println("Number of purchased tickets: $numOfTicketsPurchased")

    val percentage = numOfTicketsPurchased.toDouble() / (cinema.size * cinema[0].size) * 100
    println("Percentage: ${"%.2f".format(percentage)}%")

    println("Current income: $$currentIncome")

    val totalRows = cinema.size
    val seatsInRow = cinema[0].size
    val totalIncome = if (totalRows * seatsInRow <= 60) {
        totalRows * seatsInRow * 10
    } else {
        val halfRows = totalRows / 2
        val remainingRows = totalRows - halfRows
        halfRows * seatsInRow * 10 + remainingRows * seatsInRow * 8
    }
    println("Total income: $$totalIncome")
    showMenu(cinema)
}