#Drzewol
Drzewol jest aplikacją którą stworzyliśmy jako projekt w ramach konkursu HackHeroes 2019.
Temat główny tegorocznej edycji to "Problemy globalne". Po wielu naradach zdecydowaliśmy,
że weźmiemy na cel bardzo poważny problem w Polsce - zaśmiecanie przyrody.

##Jaki jest nasz cel?
Mamy dwa zasadnicze cele, które chcemy osiągnąć: po pierwsze chcemy ułatwić służbom porządkowym 
znajdywanie nielegalnych wysypisk śmieci. Po drugie, poprzez rosnącą bazę zgłoszeń chcemy pokazać
jak poważny jest to problem.

##Jak używać naszej apki?
To bardzo proste!
Gdy znajdziesz się w miejscu gdzie nielegalnie pozostawiono śmieci, wystarczy że włączysz aplikację,
i użyjesz formularza zgłoszeniowego, załączając zdjęcie, tytuł, opis oraz swoją lokalizację
(aplikacja Ci w tym pomoże). Jeśli jednak nie możesz dodać zgłoszenia na miejscu (na przykład
z powodu braku internetu), możesz zrobić to z innego miejsca. Wystarczy, że wcześniej zrobisz zdjęcie,
i wyślesz zgłoszenie za pomocą drugiego formularza, który pozwoli na ręczne wprowadzenie wyżej wymienionych
danych.

Jeśli chcesz zobaczyć zgłoszenia dodane przez innych użytkowników, możesz skorzystać z wbudowanej mapy.
Pinezki pokażą lokalizacje innych zgłoszeń, a po kliknięciu w jedną z nich wyświetlą się dokłądne dane.
 
##instalacja


#Rozwiązania techniczne
Aplikacja została napisana w językach Java oraz XML. Przystosowana jest do pracy na androidach werji
6.0 bądź wyższej. Jako globalny serwer danych użyliśmy darmowej wersji serwera Google Firebase,
natomiast jako serwer zdjęć Google Firestore. Mają one wiele zalet, które opisaliśmy poniżej.
W celu wyświetlania mapy wykorzystaliśmy Google Maps API, które pozwalają na proste użycie całęgo systemu
map Google.

##Google Firebase
Jest to rozbudowana, asynchroniczna baza danych która gwarantuje niezwaodność transmisji danych.
Dodatkowo jej zastosowanie w aplikacji jest stosunkowo proste i nie rodzi licznych błędów które mogą
prowadzić do zmiany a nawet utraty wysyłanych danych. Jedynym minusem tego rozwiązania są ograniczenia
darmowej wersji, które narzucają dość poważne ograniczenia w ilości przechowywanych danych oraz
dziennej ilości odczytów i zapisów na serwerze.

##Google Maps API
Pozwala ono na niezawodne wyświetlanie wszelkiego rodzaju map, umożliwiając korzystanie z wielu
fukcji opracowanych przez Google.

##


#Jak możemy dalej rozwijać aplikację?
Aktualny prorotyp jest dobrą bazą, która może być rozwinięta na wiele sposobów. 

##Zmiany które powinny zostać wprowadzone gdy aplikacja miałaby trafić na rynek:
-największą zmianą jaką chcemy wprowadzić jest implementacja sztucznej inteligencji, do oceny
    czy zgłoszenie jest prawidłowe. Gdy sztuczna inteligencja wykryłaby, że na zdjęciu nie ma śmieci
    aplikacja powiadomiła by o tym nas (developerów aplikacji) abyśmy mogli zadecydować czy umieścić
    zgłoszenie w bazie danych na stałe. Wiemy już, że takie rozwiązanie jest jak najbardziej 
    możliwe do zastosowania, ale nie zdążyliśmy go wprowadzić
-przydatne byłoby wprowadzenie panelu dla służb porządkowych, które mogłyby oznaczać, że śmieci 
    zostały usunięte
-dane na serwerze Firebase powinny zostać zabezpieczone (aktualnie nie wprowadziliśmy żadnych
    zabezpieczeń, gdyż jest to prototyp aplikacji)
-przy rosnącej ilości zgłoszeń, wymagane będzie wykupienie lepszej wersji serwera
-kod powinien zostać zoptymalizwany dla lepszej komunikacji z serwerem danych

##Pomysły na dalszy rozwój
Jak wiadomo mamy wiele problemów w których rozwiązaniu może pomóc aplikacja tego typu. Na przykład:
-zagrożenia biologiczne (np. ścieki odprowadzane do rzek)
-miejsca niebezpieczne, takie jak stare budynki

Budujądc aktualną wersję aplikacji stworzyliśmy platformę, którą łątwo rozwinąć o taki bądź inne
funkcjonalności, poprzez dodanie paru pól do wyboru typu zgłoszenia. W ten sposób można stworzyć 
skuteczny system powiadamiania o wszelkich zagrożeniach, w którym to właśnie zwykli przechodnie
mogą poinformować odpowiednie złużby o nowym zagrożeniu bądź miejscu niebezpiecznym.



