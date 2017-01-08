/* -------------------------------------------------- PARTIE 1 : SELECT --------------------------------------------------*/

1) 	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE lower(au.city) = 'oakland'

2)	SELECT a.au_lname, a.au_fname, a.address
	FROM authors a
	WHERE lower(a.au_fname) LIKE 'a%'

4) 	SELECT au.au_lname, au.au_fname
	FROM authors au
	WHERE upper(au.state) = 'CA'
	AND au.phone NOT LIKE '415%'

5)	SELECT a.au_fname, a.au_lname
	FROM authors a
	WHERE (upper(a.country) = 'BEL' OR upper(a.country) = 'NEL' OR upper(a.country) = 'LUX')

7)	SELECT DISTINCT t.pub_id
	FROM titles t
	WHERE lower(t.type) = 'psychology'
	AND (t.price > 25 OR t.price < 10)

8)	SELECT DISTINCT a.city
	FROM authors a
	WHERE (lower(a.au_fname) = 'albert'
	OR lower(a.au_lname) LIKE '%er')
	AND upper(a.state) = 'CA'

10)	SELECT DISTINCT t.type
	FROM titles t
	WHERE t.price < 15

--------------------------------------------------- PARTIE 2 : JOINTURE ----------------------------------------------------*/

1) 	SELECT t.title, t.price, p.pub_name
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id

2)	SELECT t.title, t.price, p.pub_name
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND lower(t.type) = 'psychology'

4)	SELECT DISTINCT au.state
	FROM authors au, titleauthor ta
	WHERE au.au_id = ta.au_id
	AND au.state is NOT NULL

5)	SELECT s.stor_name, s.stor_address
	FROM stores s, sales sa
	WHERE s.stor_id = sa.stor_id
	AND date_part('year', sa.date) = '1991'
	AND date_part('month',sa.date) = '11'

7)	SELECT DISTINCT t.title
	FROM titles t, titleauthor ta, authors a
	WHERE t.title_id = ta.title_id
	AND a.au_id = ta.au_id
	AND upper(a.state) = 'CA'

8)	SELECT a.au_fname, a.au_lname
	FROM authors a, titleauthor ta, titles t, publishers p
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND upper(p.state) = 'CA'

10)	SELECT DISTINCT p.pub_name
	FROM publishers p, titles t, salesdetail sd, sales sa
	WHERE t.pub_id = p.pub_id
	AND t.title_id = sd.title_id
	AND sa.ord_num = sd.ord_num
	AND sa.stor_id = sd.stor_id
	AND (sa.date > '1/11/1990' AND sa.date < '1/3/1991')

11)	SELECT s.stor_name
	FROM stores s, salesdetail sd, titles t
	WHERE s.stor_id = sd.stor_id
	AND sd.title_id = t.title_id
	AND lower(t.title) LIKE '%cook%'

13)	SELECT DISTINCT a.au_lname, a.au_fname /*ATTENTION EXPERIMENTAL */
	FROM authors a,titleauthor ta1, titleauthor ta2, titles t1, titles t2
	WHERE a.au_id = ta1.au_id
	AND a.au_id = ta2.au_id
	AND ta1.title_id = t1.title_id
	AND ta2.title_id = t2.title_id
	AND t1.title_id < t2.title_id
	AND t1.pub_id < t2.pub_id

14)	SELECT t.title, t.pubdate, sa.date
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.ord_num = sa.ord_num
	AND sd.stor_id = sa.stor_id
	AND t.pubdate > sa.date

16)	SELECT DISTINCT au.state
	FROM authors au, titleauthor ta, titles t, salesdetail sd, stores s, sales sa
	WHERE au.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.title_id = sd.title_id
	AND sd.ord_num = sa.ord_num
	AND sd.stor_id = sa.stor_id
	AND s.stor_id = sa.stor_id
	AND upper(s.state) = 'CA'
	AND date_part('year', sa.date) = '1991'
	AND date_part('month', sa.date) = '2'
	AND au.state is NOT NULL

17)	SELECT DISTINCT s1.stor_name, s2.stor_name, s1.stor_id, s2.stor_id
	FROM stores s1, stores s2, salesdetail sd1, salesdetail sd2, titleauthor ta1, titleauthor ta2
	WHERE s1.stor_id = sd1.stor_id
	AND s2.stor_id = sd2.stor_id
	AND sd1.title_id = ta1.title_id
	AND sd2.title_id = ta2.title_id
	AND ta1.au_id = ta2.au_id
	AND s1.state = s2.state
	AND s1.stor_id > s2.stor_id

19)	SELECT t.title, s.stor_name, t.price, sd.qty, sd.qty * t.price, ((sd.qty * t.price) / 100) * 2
	FROM salesdetail sd, stores s, titles t
	WHERE t.title_id = sd.title_id
	AND s.stor_id = sd.stor_id

/*--------------------------------------- PARTIE 3 : FONCTIONS D'AGREGATIONS ------------------------------------------------------*/

1)	SELECT avg(t.price)
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'

2)	SELECT a.au_lname, a.au_fname, avg(t.price)
	FROM authors a, titles t, titleauthor ta
	WHERE a.au_id = ta.au_id
	AND t.title_id = ta.title_id
	GROUP BY a.au_lname, a.au_fname

4)	SELECT t.title, t.price, count(DISTINCT sd.stor_id)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title, t.title, t.price

5)	SELECT t.title
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title
	HAVING 1 < (count(DISTINCT sd.stor_id))

7)	SELECT t.title_id, t.total_sales, sum(sd.qty)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.total_sales

8)	SELECT t.title_id, t.total_sales, sum(sd.qty)
	FROM titles t, salesdetail sd
	WHERE t.title_id = sd.title_id
	GROUP BY t.title_id, t.total_sales
	HAVING t.total_sales <> sum(sd.qty)

10)	SELECT sum(sd.qty)
	FROM titles t, publishers p, salesdetail sd, stores s
	WHERE t.pub_id = p.pub_id
	AND upper(p.state) = 'CA'
	AND t.title_id = sd.title_id
	AND sd.stor_id = s.stor_id
	AND upper(s.state) = 'CA'
	AND t.title_id IN (SELECT ta.title_id
						FROM authors a, titleauthor ta
						WHERE a.au_id = ta.au_id
						AND upper(a.state) = 'CA')

/*************************************************** PARTIE 4 : SOUS - SELECT ****************************************************************************************/

1)	SELECT t.title
	FROM titles t, publishers p
	WHERE p.pub_id = t.pub_id
	AND lower(p.pub_name) = 'algodata infosystems'
	AND t.price = (SELECT max(t1.price) FROM titles t1 WHERE t1.pub_id = p.pub_id)

2)	SELECT t.title
	FROM titles t
	WHERE 1 < (SELECT count(DISTINCT sd.stor_id) FROM salesdetail sd WHERE sd.title_id = t.title_id)

4)	SELECT DISTINCT a.au_lname, a.au_fname
	FROM authors a, titleauthor ta, titles t, publishers p
	WHERE a.au_id = ta.au_id
	AND ta.title_id = t.title_id
	AND t.pub_id = p.pub_id
	AND lower(a.state) = lower(p.state)

5)	SELECT p.pub_name
	FROM publishers p
	WHERE NOT EXISTS (SELECT t.title_id FROM titles t WHERE t.pub_id = p.pub_id)

7)	SELECT p.pub_name, count(t.title_id)
	FROM publishers p, titles t
	WHERE t.pub_id = p.pub_id
	GROUP BY p.pub_name
	HAVING count(t.title_id) = 0

8)	SELECT t.title_id, t.title
	FROM titles t, publishers p
	WHERE t.pub_id = p.pub_id
	AND upper(p.state) = 'CA'
	AND 'CA' = ALL(SELECT a.state FROM authors a, titleauthor ta WHERE ta.title_id = t.title_id AND ta.au_id = a.au_id)
	AND EXISTS (SELECT ta.au_id FROM titleauthor ta WHERE ta.title_id = t.title_id)
	AND 'CA' = ALL(SELECT s.state FROM stores s, salesdetail sd WHERE sd.stor_id = s.stor_id AND sd.title_id = t.title_id)
	AND EXISTS (SELECT sd.stor_id FROM salesdetail sd WHERE sd.title_id = t.title_id)

9)	SELECT DISTINCT t.title
	FROM titles t, salesdetail sd, sales sa
	WHERE t.title_id = sd.title_id
	AND sd.ord_num = sa.ord_num
	AND sd.stor_id = sa.stor_id
	AND sa.date = (SELECT max(sa1.date) FROM sales sa1)

11)	SELECT DISTINCT a.city
	FROM authors a
	WHERE upper(a.state) = 'CA' 
	AND a.city NOT IN (SELECT s.city FROM stores s WHERE upper(s.state) = 'CA')

13)	SELECT DISTINCT t.title
	FROM titles t
	WHERE 'CA' = ALL(SELECT upper(a.state) FROM authors a, titleauthor ta WHERE a.au_id = ta.au_id AND ta.title_id = t.title_id)

14)	SELECT t.title
	FROM titles t
	WHERE t.title_id NOT IN (SELECT ta.title_id FROM titleauthor ta, authors a WHERE a.au_id = ta.au_id AND upper(a.state) = 'CA')

16)	SELECT t.title
	FROM titles t, titleauthor ta
	WHERE t.title_id IN (SELECT ta1.title_id FROM titleauthor ta1, authors a WHERE ta1.au_id = a.au_id AND upper(a.state) = 'CA')
	AND t.title_id = ta.title_id
	GROUP BY t.title
	HAVING count(ta.au_id) = 1 

/*************************************************** PARTIE 5 : UNION, INTERSECTION, DIFFERENCE ***************************************************************************/

1)	SELECT s.stor_name, sum(sd.qty * t.price)
	FROM stores s LEFT OUTER JOIN salesdetail sd ON s.stor_id = sd.stor_id LEFT OUTER JOIN titles t ON t.title_id = sd.title_id
	GROUP BY s.stor_name
	ORDER BY s.stor_name

2)	SELECT s.stor_name, sum(sd.qty * t.price) as "chiffre_affaire"
	FROM stores s LEFT OUTER JOIN salesdetail sd ON s.stor_id = sd.stor_id LEFT OUTER JOIN titles t ON t.title_id = sd.title_id
	GROUP BY s.stor_name
	ORDER BY "chiffre_affaire" DESC

4)	SELECT t.title, t.type, a.au_lname, a.au_fname, t.price
	FROM titles t LEFT OUTER JOIN titleauthor ta ON t.title_id = ta.title_id LEFT OUTER JOIN authors a ON a.au_id = ta.au_id
	WHERE t.price > 20
	ORDER BY t.type

5)	((SELECT a.city FROM authors a WHERE upper(a.state) = 'CA') 
UNION (SELECT p.city FROM publishers p WHERE upper(p.state) = 'CA')) 
EXCEPT (SELECT s.state FROM stores s WHERE upper(s.state) = 'CA')

7)	SELECT a.au_fname, a.au_lname, a.adress, t.title_id, t.title, 
	FROM authors a LEFT OUTER JOIN titleauthor ta ON ta.au_id = a.au_id LEFT OUTER JOIN titles t ON t.title_id = ta.title_id
	ORDER BY t.title_id 
