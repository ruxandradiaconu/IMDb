Nume : Diaconu Oana
Ruxandra
Grupa : 321CC
README Tema POO 2023
Consider ca nivelul de dificultate al temei este unul intermediar, deoarece poate fi rezolvat de un student care nu a mai avut cunostinte de baza in Java 
pana in acest semestru, aplicand o parte din cunostintele de baza de la laborator si conferindu-le o aplicabilitate intr-un proiect mai complex si amplu. 
Tema am realizat-o majoritar in vacanta de iarna, adica in decursul a 2 saptamani, cu cateva ore pe zi de lucrat.
Implementarea mea a fost cea din cerinta, respectand indicatiile si observatiile scrise acolo, dar am incercat de asemenea sa aplic o abordare cat mai logica 
si aplicabila in viata reala pentru functionalitatile aplicatiei.

In proiect am declarat in pachetul org.example clasele : Actor, Admin, Contributor, Credentials, Episode, IMDB, Movie, Production, Rating, Regular, 
Request, RequestHolder, Series, Staff, User; enumeratiile: AccountType, Genre, RequestTypes si interfetele: ComparableItem, RequestsManager si StaffInterface. 
Fiecare clasa are parametrii si metodele descrise din cerinta, fiind initiate toate ca ‘private’ si folosind gettere si settere la fiecare.

Design pattern-urile implementate sunt : Singleton, Builder pentru clasa Information, care e interna clasei User si Factory.

Clasa IMDB este cea care contine metoda ‘main’ care asigura rularea programului. Datele sunt parsate din cele 4 fisiere JSON de input date cu ajutorul 
bibliotecii org.json.simple si am 4 metode corespunzatoare fiecarui fisier si lista cu elemente corespunzatoare fisierului. 
Datele le-am introdus in listele mele cu ajutorul variabilelor de tip JSONArray si JSONObject. 

Daca vreun utilizator din fisierul accounts.json nu are credentialele complete sau nume, se va arunca o exceptie de tip InformationIncompleteException.
Am realizat doar optiunea de terminal si chiar daca la inceputul testarii lasa utilizatorul sa aleaga intre CLI si GUI, 
nu poate alege decat prima varianta, adica CLI(terminalul), altfel intra intr-un loop infinit pana cand ar fi aleasa aceasta varianta. 
Citirea credentialelor utilizatorului si a restul informatiilor pe care acesta urmeaza sa le introduca din terminal sunt facute cu ajutorul unui scanner.
Dupa ce utilizatorul introduce varianta “1” (CLI), i se va cere sa isi introduca credentialele pentru a se autentifica in cont. 
In cazul in care emailul introdus nu se afla in baza de date tocmai citita din fisierul json sau parola scrisa nu este cea asignata emailului 
cu care vrea sa se autentifice, va primi un mesaj care ii spune sa repete actiunea pana cand se va loga. 
Cand va reusi, vor aparea in terminal username-ul si experienta utilizatorului respectiv si cele 8 (Regular) sau 10 (Contributor/Admin) optiuni 
din care poate sa aleaga in functie de tipul de utilizator al credentialelor introduse. 
Atunci cand incepe aplicatia si dupa finalul fiecarei optiuni completata se afiseaza in terminal iar lista cu toate functionalitatile pe care le poate 
realiza utilizatorul. Pentru alegerea optiunilor am folosit un switch statement pentru a categoriza in functie de tipul de utilizator si pentru fiecare tip, 
inca cate un switch statement cu 8/10 case-uri ce reprezinta fiecare optiune a sa.

Am implementat din lista totala de optiuni: vizualizarea detaliilor tuturor productiilor din sistem, vizualizarea detaliilor tuturor actorilor din sistem, 
vizualizarea notificarilor primate, cautarea unui anumit film/serial/actor, adaugarea/stergerea unei productii/actor la/din lista de favorite, 
crearea/retragerea unei cereri, actualizarea informatiilor despre productii/actori, adaugarea/stergerea unei recenzii pentru o productie, 
delogarea si adaugarea/stergerea unui actor din sistem.

Fiecare optiune unde se are de ales intre add/delete, intre movie/actor/series etc daca nu este introdusa o varianta valida dintre cele date, 
se arunca o exceptie de tip InvalidCommandException si se va afisa un mesaj prin care se cere iar sa se introduca una din variantele date, 
pana cand una din ele este corecta.
1. Vizualizarea detaliilor tuturor productiilor din sistem: are optiunea de a filtra productiile
dupa gen, afisandu se fiecare gen si filmele sau serialele care fac parte din el , sau dupa
numarul de rating uri. M am folosit de metoda viewProductionDetails.
2. Vizualizarea detaliilor tuturor actorilor din sistem: are optiunea de a filtra actorii in functie
de numele acestora, din punct de vedere alfabetic si este realizata in functia
viewActorsDetails.
3. Vizualizarea notificarilor primite: se afiseaza prin metoda viewNotifications(user) toate
notificarile primite de la alti utilizatori in functie de ce alte date a mai introdus pana atunci.
4. Cautarea unui anumit film/serial/actor : user ul introduce ce fel de obiect
cauta(movie/series/actor) si i se afiseaza toate detaliile despre acel obiect cautat cu
metodele searchActor, searchProduction care filtreaza automat daca e movie/series.
5. Adaugarea/stergerea unei productii la/din lista de favorite: se intreaba mai intai daca vrea sa
adauge sau sa stearga, dupa care introduce daca vrea actor/movie/series si i se va afisa lista
sa de favorite inainte si dupa actiunea facuta. Functionalitatea aceasta se realizeaza cu
metodele: addActorFavorites, addMovieFavorites, addSeriesFavorites,
removeActorFavorites, removeMovieFavorites si removeSeriesFavorites in functie de ceea ce
vrea utilizatorul sa faca.
6. Crearea/retragerea unei cereri: se intreaba care varianta vrea sa o realizeze add sau delete,
dupa care se apeleaza in functie de caz createRequest sau removeRequest. La functia
removeRequest difera modul de afisare in functie de numarul de cereri deja facute: doar un
mesaj daca nu are ce sa stearga, stergere automata daca are doar o cerere creata anterior
sau este pus sa aleaga dintr o lista de cereri ale sale daca are mai multe.
7. Adaugarea/stergerea unui actor din sistem: se apeleaza functiile addActorToSystem sau
deleteActorFromSystem d in clasa IMDB in functie de alegerea user ului si
addActorSystem/r em oveActorSystem din Staff . Daca se vrea adaugarea unui actor nou,
acesta va trebui sa introduca fiecare detaliu necesar, iar la stergere va putea sterge doar pe
cei pe care i a introdus el anterior si se afla in contribution list
8. Actualizarea informatiilor despre productii/actori: se alege daca vrea modificarea unor
detalii a unui actor sau productie in parte si are o lista din care poate alege ce anume vrea sa
actualizeze, dar daca este o lista poate sa aleaga in principiu daca sa adauge sau sa retraga
vreun nume din acea lista. La fel ca la adaugarea/stergerea din sistem, aceasta functie se
poate realiza doar la actorii sau productiile introduse de el in sistem si care se afla in
contribution list cu ajutorul metodelor updateActor si updateProduction din clasa IMDB, dar
si updateActor si updateProduction din clasa Staff.
9. Adaugarea/stergerea unei recenzii pentru o productie de catre userii Regular cu functiile
createRating sau removeRating.
10. Delogarea: la aceasta optiune are posibilitatea sa se deconecteze de la contul curent si sa se
poata conecta iar cu ajutorul credentialelor sau sa se deconecteze cu totul si sa inchida
aplicatia. Metoda este relalizata in clasa User.

Am mai folosit metodele credentialsLogin pentru verificarea daca informatiile introduse de user la
inceput sunt asociate corespunzator unui account deja valabil; getStringList sau getJSONObjectList care
ma ajuta pentru parsarea informatiilor din fisierele json la citire; getFormattedDateTime_Request si
getFormattedDateTime_Account care ma ajuta sa formatez datele din fisierele json in functie de tipul de
localDateTime de variabila dorit in clasa respectiva.
Pentru bonus: cele 4 metode modify(Accounts/Requests/Actors/Productions)InJSON sunt cele
care vor fi apelate la optiunea de logout cu inchiderea aplicatiei pentru a salva modificarile facute de
utilizatori in fisierele .json in functie de ce anume s-a schimbat la lista de productions, actors sau
requests.
