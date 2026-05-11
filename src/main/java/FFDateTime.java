public class FFDateTime {
    /*
    Zdecydowałem się na użycie klasy java.time.LocalDateTime zamiast implementacji własnej klasy FFDateTime
    Wydało mi się to trochę prostsze i bardziej profesjonalne podejście
    Natomiast zauważyłem, że ma to swoje wady i zalety
    Zalety:
    Kod staje się od razu zrozumiały dla każdego programisty Javy. Nie ma potrzeby uczenia się "wewnętrznego" API (np. metody toEpochMinutes)
    Wbudowane klasy są przetestowane przez miliony użytkowników, co eliminuje ryzyko pomyłek przy ręcznym implementowaniu przenoszenia reszty z minut na godziny czy z dni na miesiące.
    Odejście od uproszczenia 30 dni w miesiącu

    Wady:
    Przy implementacji własnej klasy czasu można zdefiniować bardzo nietypowe wymagania
    Tak jak poruszaliśmy na zajęciach, podałeś przykład projektu na którym pracujesz, że LocalDateTime zostało opakowane w inną własną klasę

    Różnice:
    Walidacja
    Własna klasa wymagałaby napisania ręcznych warunków (np. if (month < 1 || month > 12)). LocalDateTime (i parser w java.time) zajmuje się walidacją automatycznie.
    Zablokuje próbę utworzenia daty takiej jak 31 lutego, natychmiast rzucając wbudowany DateTimeException. Dodatkowo walidacja logiczna (np. end > start) sprowadza się do wywołania jednej wbudowanej metody.
    Arytmetyka
    Zamiast ręcznie przeliczać wszystko na minuty od wymyślonej epoki, arytmetyka w java.time opiera się na gotowych, bezpiecznych metodach.
    Dodawanie czasu realizuję przez start.plusMinutes(durationMinutes), a wyliczanie różnicy w czasie (np. dla cennika) rozwiązuję za pomocą klasy pomocniczej ChronoUnit.MINUTES.between(start, end).
    Czytelność
    Porównywanie dat stało się znacznie bardziej naturalne.
    Zamiast technicznego compareTo() < 0 lub arytmetyki na liczbach całkowitych (epoch), mogę używać metod takich jak start.isBefore(end) czy start.isAfter(end), które czyta się niemal jak zwykłe zdania w języku angielskim.
     */
}
