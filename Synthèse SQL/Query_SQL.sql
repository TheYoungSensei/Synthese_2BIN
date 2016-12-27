/* 2.a.ii */

1) 	SELECT au.au_lname, a.au_fname
	FROM authors au
	WHERE lower(au.city) = lower(‘Oakland’)

2)	SELECT au.au_lname, au.adress
	FROM authors au
	WHERE upper(au.au_fname) LIKE ‘A%’

3)	SELECT au.au_lname, au.adress
	FROM authors au
	WHERE au.phone is NULL OR au.phone = ‘’

4)	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE au.state = ‘CA’
	AND au.phone NOT LIKE ‘415%’

5)	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE au.country = ‘BEL’
	OR au.country = ‘LUX’
	OR au.country = ‘NED’

OR

5’)	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE au.country IN(‘BEL’,’LUX’,’NED’)

6)	SELECT DISTINCT p.pub_id
	FROM publishers p, titles t
	WHERE p.pub_id = t.pub_id
	AND lower(t.type) = ‘psychology’


7)	SELECT DISTINCT t.pub_id
	FROM titles t
	WHERE lower(t.type) = 'psychology'
	AND (t.price < 10 OR t.price > 25)

8)	SELECT DISTINCT au.city
	FROM authors au
	WHERE (lower(au.au_fname) = ‘albert’
	OR au.au_fname LIKE ‘%er’)
	AND upper(state) = ‘CA’

9) 	SELECT DISTINCT au.state, au.country
	FROM authors au
	WHERE au.state IS NOT NULL AND au.state <> ‘’
	AND lower(au.country) <> ‘usa’

10)	SELECT DISTINCT t.type
	FROM titles t
	WHERE t.price < 15

/* 2.b.iv */

1) 	SELECT t.title, t.price, p.pub_name, t.title_id
	FROM titles t, publishers p
	WHERE p.pub_id=t.pub_id

2) 	SELECT t.title, t.price, p.pub_name, t.title_id
	FROM titles t, publishers p
	WHERE p.pub_id=t.pub_id
	AND lower(t.type) = 'psychology'

3) 	SELECT DISTINCT a.au_lname, a.au_fname, a.au_id
	FROM authors a, titleauthors ta
	WHERE a.au_id = ta.au_id

4) 	SELECT DISTINCT a.state
	FROM authors a, titleauthor ta
	WHERE a.au_id = ta.au_id
	AND a.sate IS NOT NULL 

5) 	SELECT DISTINCT st.stor_name, st.stor_address, sa.date
	FROM stores st, sales sa
	WHERE st.stor_id = sa.stor_id
	AND date_part('month',sa.date) = 11
	AND date_part('year', sa.date) = 1991

	OR

5bis) SELECT DISTINCT st.stor_name, st.stor_address, sa.date
	FROM stores st, sales sa
	WHERE st.stor_id = sa.stor_id
	AND sa.date < '1991-12-1'
	AND sa.date >= '1991-11-1'

6) 	SELECT t.title
	FROM titles t, publishers p
	WHERE t.price < 20 
	AND lower(t.type) = 'psychology'
	AND t.pub_id = p.pub_id
	AND lower(p.pub_name) NOT LIKE 'algo%'

7) 	SELECT DISTINCT t.title
	FROM titles t, titleauthor ta, authors a
	WHERE ta.title_id = t.title_id
	AND ta.au_id = a.au_id
	AND upper(a.state) = 'CA'

8) 	SELECT DISTINCT a.au_fname, a.au_lname
	FROM authors a, titleauthor ta, titles t, publishers p
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND upper(p.state) = 'CA'

9) 	SELECT DISTINCT a.au_fname, a.au_lname
	FROM authors a, titleauthor ta, titles t, publishers p
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND p.state = a.state

10)	SELECT DISTINCT p.pub_name, p.pub_id
	FROM publishers p, titles t, salesdetail sd, sales sa
	WHERE p.pub_id = t.pub_id
	AND sd.title_id = t.title_id
	AND sd.stor_id = sa.stor_id
	AND sd.ord_num = sa.ord_num
	AND sa.date > '1/11/1990' AND sa.date < '1/3/1991'

11) SELECT DISTINCT s.stor_name, s.stor_id
	FROM stores s, salesdetail sd, titles t
	AND sd.stor_id = s.stor_id
	AND sd.title_id = t.title_id
	AND lower(t.title) SIMILAR TO '%cook%'


12)	SELECT t1.title as 'livre_1', t2.title as 'livre_2', t1.title_id as 'id_1', t2.title_id as 'id_2'
	FROM titles t1, titles t2
	WHERE t1.pub_id = t2.pub_id
	AND t1.pubdate = t2.pubdate
	AND t1.title_id > t2.title_id

13)	SELECT DISTINCT a.au_lname, a.au_fname
	FROM authors a, titles t1, titles t2, titleauthor ta
	WHERE ta.au_id = a.au_id
	AND ta.title_id = t1.title_id
	AND ta.title_id = t2.title_id
	AND t1.pub_id <> t2.pub_id
OR

13bis) SELECT a.au_lname, a.au_fnme, a.au_id
	FROM authors au_id
	WHERE 1 > (	SELECT count DISTINCT t.pub_id
				FROM titleauthor ta, titles t
				WHERE ta.title_id = t.title_id
				AND a.au_id = ta.au_id)

14)	SELECT DISTINCT t.title
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.stor_id = sa.stor_id
	AND sd.ord_num = sa.ord_num
	AND t.pubdate > sa.date

15)	SELECT DISTINCT s.stor_name
	FROM stores s, salesdetail sd,
	 titleauthor ta, authors a
	WHERE sd.stor_id = s.stor_id
	AND ta.title_id = sd.title_id
	AND a.au_id = ta.au_id
	AND lower(a.au_lname) = 'ringer'
	AND lower(a.au_fname) = 'anne'

16)	SELECT DISTINCT a.state
	FROM authors a, titleauthor ta,
	salesdetail sd, sales sa, stores s
	WHERE s.stor_id = sa.stor_id
	AND sd.stor_id = s.stor_id
	AND sa.ord_num = sd.ord_num
	AND sd.title_id = ta.title_id
	AND a.au_id = ta.au_id
	AND upper(s.state) = 'CA'
	AND date_part('year', sa.date) = '1991'
	AND date_part('month', sa.date) = '2'
	AND a.state is NOT NULL

17) SELECT DISTINCT st1.stor_name, st2.stor_name, st1.stor_id, st2.stor_id
	FROM stores st1, stores st2, salesdetail sd1, salesdetail sd2, titleauthor ta1, titleauthor ta2
	WHERE st1.state = st2.state
	AND st1.stor_id > st2.stor_id
	AND sd1.stor_id = st1.stor_id
	AND sd2.stor_id = st2.stor_id
	AND ta1.title_id = sd1.title_id
	AND ta2.title_id = sd2.title_id
	AND ta1.au_id = ta2.au_id


18)	SELECT DISTINCT a1.au_id, a1.au_lname, a1.au_fname,
 	a2.au_id, a2.au_lname, a2.au_fname
 	FROM authors a1, authors a2, titleauthor ta1,
  	titleauthor ta2
 	WHERE a1.au_id = ta1.au_id
 	AND a2.au_id = ta2.au_id
 	AND a1.au_id > a2.au_id
 	AND ta1.title_id = ta2.title_id

19)	SELECT sd.stor_id, sd.ord_num, sd.title_id, t.title, st.stor_name, t.rpice * sd.qty as "total", ((t.price * sd.qty)/100) * 2 as "eco tax", t.price
	FROM titles t, salesdetail sd, stores st
	WHERE t.title_id = sd.title_id
	AND sd.stor_id = st.stor_id

/* 2.d.ii */

1)	SELECT avg(t.price)
	FROM titles t, publishers p
	WHERE p.pub_id = t.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'

2) 	SELECT a.au_id, a.au_fname, a.au_lname, avg(t.price)
	FROM titles t, authors a, titleauthor ta
	WHERE t.title_id = ta.title_id
	AND ta.au_id = a.au_id
	GROUP BY a.au_id, a.au_fname, a.au_lname

3)	SELECT t.title_id, t.title, t.price, count(/*DISTINCT*/ ta.au_id)
	FROM titles t, titleauthor ta, publishers p
	WHERE t.title_id = ta.title_id
	AND p.pub_id = t.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'
	GROUP BY t.title_id, t.title, t.price

4)	SELECT t.title_id, t.title, t.price, count(DISTINCT sd.stor_id)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.title, t.price

5) 	SELECT t.title_id, t.title, t.price, count(DISTINCT sd.stor_id)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.title, t.price
	HAVING count(DISTINCT sd.stor_id) > 1

6)	SELECT t.type, count(t.title_id), avg(t.price)
	FROM titles t
	GROUP BY t.type

7)	SELECT t.title_id, t.total_sales, sum(sd.qty)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.total_sales

8) 	SELECT t.title_id, t.total_sales, sum(sd.qty)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.total_sales
	HAVING sum(sd.qty) <> t.total_sales

9) 	SELECT t.title_id, t.title, count(DISTINCT ta.au_id)
	FROM titles t, titleauthor ta
	WHERE t.title_id = ta.title_id
	GROUP BY t.title_id, t.title
	HAVING count(DISTINCT ta.au_id) >= 3

10)	SELECT sum(sd.qty)
	FROM titles t, salesdetails sd, stores st, publishers p
	WHERE p.pub_id = t.pub_id
	AND t.title_id = sd.title_id
	AND sd.stor_id = st.stor_id
	AND upper(s.state) = 'CA'
	AND upper(p.state) = 'CA'
	AND t.title_id IN (SELECT ta.title_id
						FROM authors a, titleauthor ta
						WHERE a.au_id = ta.au_id
						AND upper(a.state) = 'CA')

/* 2.e.iv */

1)	SELECT t.title_id, t.title
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'
	AND t.price = 	(SELECT MAX(t2.price) 
					 FROM titles t2
					 WHERE t2.pub_id = p.pub_id)

2)
	/* SOUS SELECT */

	SELECT t.title, t.title_id
	FROM titles t
	WHERE 2 <= (SELECT count(DISTINCT sd.stor_id)
				FROM salesdetail sd 
				WHERE t.title_id = sd.title_id)

	/* JOINTURE */

	SELECT DISTINCT t.title_id, t.title_id
	FROM titles t, salesdetail sd1, salesdetail sd2
	WHERE t.title_id = sd1.title_id
	AND t.title_id = sd2.title_id
	AND sd2.stor_id <> sd1.stor_id

	/* GROUP BY */

	SELECT t.title_id, t.title_id
	FROM salesdetail sd, titles titleauthor
	WHERE t.title_id = sd.title_id
	GROUP BY sd.title_id
	HAVING count(DISTINCT sd.stor_id) > 1


3)	SELECT t1.title, t1.title_id
	FROM titles t1
	WHERE t1.price > 	(SELECT avg(t2.price) * 1.5 
						 FROM titles t2 
						 WHERE t1.type = t2.type)

4) 	SELECT DISTINCT a.au_id, a.au_fname, a.au_lname
	FROM authors a, publishers p, titles t, titleauthor ta
	WHERE a.au_id = ta.au_id
	AND t.title_id = ta.title_id
	AND t.pub_id = p.pub_id
	AND p.state = a.state

5)	SELECT p.pub_id, p.pub_name
	FROM publishers p
	WHERE p.pub_id NOT EXISTS (SELECT t.title_id 
								FROM titles t
								WHERE p.pub_id = t.pub_id)

6)	SELECT p.pub_id, p.pub_name
	FROM publishers p
	WHERE (SELECT count(t.title_id)
			FROM titles t
			WHERE t.pub_id = p.pub_id) >= ALL (SELECT count(t2.title_id)
												FROM titles t2 
												GROUP BY t2.pub_id)

7)	/* SUB SELECT */

	SELECT p.pub_id, p.pub_name
	FROM publishers p
	WHERE p.pub_id NOT IN 	(SELECT t.pub_id 
							 FROM titles t, salesdetail sd
							 WHERE t.title_id = sd.title_id)

	/* EXCEPT */

	SELECT p.pub_id, p.pub_name
	FROM publishers p
	EXCEPT
	SELECT p2.pub_id, p2.pub_name
	FROM titles t, publishers p2
	WHERE t.pub_id = p2.pub_id

8)	SELECT t.title_id, t.title
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND p.state = 'CA'
	AND 'CA' = ALL (SELECT a.state FROM authors a, titleauthor ta WHERE a.au_id = ta.au_id AND t.title_id = ta.title_id)
	AND EXISTS (SELECT ta.au_id FROM titleauthor ta WHERE ta.title_id = t.title_id)
	AND 'CA' = ALL (SELECT st.state FROM stores st, salesdetails sd WHERE st.stor_id = sd.stor_id AND sd.title_id = t.title_id)
	AND EXISTS (SELECT sd.title_id FROM salesdetail WHERE sd.title_id = t.title_id)

OR
	SELECT DISTINCT t.title_id, t.title
	FROM titles t, authors a, titleauthor ta, publishers p, salesdetails sd, stores st
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND t.title_id = sd.title_id
	AND sd.title_id = st.stor_id
	AND upper(a.state) = 'CA'
	AND upper(st.state) = 'CA'
	AND upper(p.state) = 'CA'
	AND t.title_id NOT IN (SELECT sd2.title_id FROM salesdetail sd2, stores st2 WHERE st2.stor_id = sd2.stor_id AND upper(st2.state) != 'CA')


9)	SELECT DISTINCT t.title
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.stor_id = sa.stor_id
	AND sd.ord_num = sa.ord_num
	AND sa.date = (SELECT max(sa.date) FROM sales sa)

OR
	SELECT DISTINCT t.title
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.stor_id = sa.stor_id
	AND sd.ord_num = sa.ord_num
	AND sa.date >= ALL(SELECT sa.date FROM sales sa WHERE sa.date is NOT NULL)

10) SELECT st1.stor_id, st1.stor_name
	FROM stores st1, salesdetail sd1, stores st2, salesdetail sd2
	WHERE st1.stor_id = sd1.stor_id /*On regarde le nombre de livres vendus par bookbeat et on regarde le nombre de livre egalement vendus par bookbeat et ce magasin */
	AND st2.stor_id = sd2.stor_id
	AND sd1.title_id = sd2.title_id
	AND lower(st2.stor_name) = 'bookbeat'
	GROUP BY st1.stor_id
	HAVING count(DISTINCT sd1.title_id) = (SELECT count(DISTINCT sd3.title_id) /*On regarde que le magasin possede bien le nombre de livres vendus que bookbeat */
											FROM salesdetail sd3, stores st3 
											WHERE sd3.stor_id = st3.stor_id 
											AND lower(st3.stor_name) = 'bookbeat')
	AND st1.stor_name <> 'Bookbeat' /* Bookbeat a forcement le meme nombre de livres vendus que lui même */

11)	SELECT DISTINCT a.city
	FROM authors a
	WHERE a.state = 'CA' AND NOT EXISTS (SELECT * FROM stores st WHERE a.city = st.city AND st.state = 'CA')

12) SELECT DISTINCT p.pub_id, p.pub_name
	FROM publishers p
	WHERE (SELECT count(a.au_id)
			FROM authors a
			WHERE p.city = a.city) >= ALL (SELECT count(a2.au_id) 
											FROM authors a2
											GROUP BY a2.city)

13) SELECT DISTINCT t.title_id, t.title /* Version non corrigée */
	FROM titles t
	WHERE 'CA' = ALL(SELECT a.state FROM authors a, titleauthor ta WHERE a.au_id = ta.au_id AND t.title_id = ta.title_id)

OR 

	SELECT DISTINCT t.title, t.title_id
	FROM titles t, titleauthor ta
	WHERE t.title_id = ta.title_id
	AND t.title_id NOT IN (SELECT ta2.title_id FROM authors a ,titleauthor ta2 WHERE upper(a.state) <> 'CA' AND ta2.au_id = a.au_id) 

14)	SELECT t.title_id, t.title /* Non corrigé */
	FROM titles t
	WHERE 'CA' <> ALL(SELECT a.state FROM authors a, titleauthor ta WHERE a.au_id = ta.au_id AND t.title_id = ta.title_id)

OR

	SELECT t.title_id, t.title
	FROM titles t
	WHERE 0 = (SELECT count(a.au_id) FROM titleauthor ta, authors a WHERE ta.title_id = t.title_id AND ta.au_id = a.au_id AND lower(a.state) = 'ca')

15) SELECT t.title_id, t.title
	FROM titles t
	GROUP BY t.title_id, t.title
	HAVING 1 = (SELECT count(ta.au_id) FROM titleauthor ta WHERE ta.title_id = t.title_id)

16) SELECT t.title_id, t.title
	FROM titles t
	WHERE 0 = (SELECT count(ta.au_id) FROM titleauthor ta, authors a WHERE ta.title_id = t.title_id AND a.au_id = ta.au_id AND upper(a.state) = 'CA')

OR

	SELECT ta.title_id, t.title
	FROM titles t, titleauthor ta
	WHERE t.title_id = ta.title_id
	AND ta.title_id IN (SELECT ta2.title_id
							FROM authors a, titleauthor ta2
							WHERE upper(a.state) = 'CA'
							AND ta2.au_id = a.au_id)
	GROUP BY ta.title_id
	HAVING count(ta.au_id) = 1

/* 2.g.ii */

1)	SELECT st.stor_id, st.stor_name, sum(sd.qty * t.price)
	FROM stores st LEFT OUTER JOIN salesdetail sd ON st.stor_id = sd.stor_id LEFT OUTER JOIN titles t ON t.title_id = sd.title_id
	GROUP BY st.stor_id, st.stor_name
	ORDER BY st.stor_name ASC

OR

	(SELECT st.stor_id, st.stor_name, sum(sd.qty * t.price)
	FROM stores st, salesdetail sd, titles t
	WHERE st.stor_id = sd.stor_id
	AND t.title_id = sd.title_id
	GROUP BY st.stor_id, st.stor_name) UNION (SELECT st.stor_id, st.stor_name, 0
										FROM stores st
										WHERE st.stor_id NOT IN (SELECT sd1.stor_id FROM salesdetail sd1))
	ORDER BY stor_name ASC

2)	SELECT st.stor_id, st.stor_name, sum(sd.qty * t.price) as "Chiffre d'Affaire"
	FROM stores st LEFT OUTER JOIN salesdetail sd ON st.stor_id = sd.stor_id LEFT OUTER JOIN titles t ON t.title_id = sd.title_id
	GROUP BY st.stor_id, st.stor_name
	ORDER BY "Chiffre d'Affaire" DESC

3)	SELECT t.type, t.title, p.pub_name, t.price
	FROM titles t LEFT OUTER JOIN publishers p ON t.pub_id = p.pub_id
	WHERE t.price > 20
	ORDER BY t.type ASC

4)	SELECT t.type, t.title, p.pub_name, t.price, a.au_lname, a.au_fname
	FROM titles t LEFT OUTER JOIN publishers p ON t.pub_id = p.pub_id LEFT OUTER JOIN titleauthor ta ON t.title_id = ta.title_id LEFT OUTER JOIN authors a ON a.au_id = ta.au_id
	WHERE t.price > 20
	ORDER BY t.type ASC

5)	((SELECT a.city FROM authors a WHERE upper(a.state) = 'CA') UNION (SELECT p.city FROM publishers p WHERE upper(p.state) = 'CA'))
		 EXCEPT (SELECT st.city FROM stores st WHERE upper(st.state) = 'CA')

6)	SELECT a.au_lname, a.au_fname, count(DISTINCT t.title_id) as "total"
	FROM authors a LEFT OUTER JOIN titleauthor ta ON a.au_id = ta.au_id 
					LEFT OUTER JOIN titles t ON t.title_id = ta.title_id
	WHERE t.price > 20
	GROUP BY a.au_lname, a.au_fname
	ORDER BY "total" DESC, a.au_lname ASC, a.au_fname ASC

7)	SELECT a.au_fname, a.au_lname, a.address, t.title_id, t.title
	FROM authors a FULL OUTER JOIN titleauthor ta ON a.au_id = ta.au_id
					FULL OUTER JOIN titles t ON t.title_id = ta.title_id
	ORDER BY t.title_id ASC
