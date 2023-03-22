from flask import*
from database import*

api=Blueprint('api',__name__)

@api.route('/logins')
def logins():
	data={}
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	else:
		data['status']='faild'
	return str(data)



@api.route('/Registration')
def Registration():
	data={}
	
	f=request.args['fname']
	l=request.args['lname']
	c=request.args['city']
	h=request.args['hname']
	s=request.args['street']
	u=request.args['username']
	p=request.args['password']
	q="select * from login where username='%s' and password='%s'"%(u,p)
	res=select(q)
	if res:
		data['status']='already'
	else:
		q="insert into login values(null,'%s','%s','pending')"%(u,p)
		id=insert(q)
		q="insert into rider values(null,'%s','%s','%s','%s','%s','%s','0','0')"%(id,f,l,c,h,s)
		insert(q)
		q="insert into wallet values(null,'0','%s')"%(id)
		insert(q)
		data['status']='success'
	return str(data)



@api.route('/viewspinner')
def viewspinner():
	data={}
	q="select * from cycle where status='Avaliable'"
	res=select(q)
	if res:
		
		data['data']=res
		data['status']='success'
		data['method']='viewspinner'
	return str(data)


@api.route('/BookCycle')	
def BookCycle():
	data={}
	login_id=request.args['login_id']
	hours=request.args['hours']
	total=request.args['total']
	cid=request.args['cid']



	q="insert into booking values(null,(select r_id from rider where login_id='%s'),'%s','%s','%s','Booked')"%(login_id,cid,total,hours)
	insert(q)
	q="update cycle set status='Booked' where c_id='%s'"%(cid)
	update(q)
	data['status']='success'
	data['method']='Bookcycle'
	return str(data)


@api.route('/Viewbookings')	
def Viewbookings():
	data={}
	login_id=request.args['login_id']

	q="select *,concat(booking.status) as bstatus from booking inner join rider using (r_id) inner join cycle using (c_id) inner join cycle_station using(s_id) where r_id=(select r_id from rider where login_id='%s')"%(login_id)
	res=select(q)
	if res:
		data['data']=res
		
		data['status']='success'
	return str(data)


@api.route('/Complaint')
def Complaint():
	data={}
	c=request.args['compliant']
	l=request.args['login_id']
	cid=request.args['cid']
	fd=request.args['fromdate']

	q="insert into complaint values(null,(select r_id from rider where login_id='%s'),'%s','%s','%s')"%(l,cid,fd,c)
	insert(q)
	data['status']='success'
	return str(data)

@api.route('/Viewcyclestation')
def Viewcyclestation():
	data={}
	q="select * from cycle_station"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	return str(data)


@api.route('/Makepayment')
def Makepayment():
	data={}
	amount=request.args['amount']
	login_id=request.args['login_id']
	bid=request.args['bid']
	sid=request.args['sid']


	q="select * from wallet where amount>='%s' and login_id='%s'"%(amount,login_id)

	res=select(q)
	print(q)
	if res:
		data['status']='already'

	else:
		q="insert into payment values(null,'%s',(select r_id from rider where login_id='%s'),curdate(),'%s')"%(amount,login_id,bid)
		insert(q)
		q="update wallet set amount=amount-'%s' where login_id='%s'"%(amount,login_id)
		update(q)
		q="update booking set status='Paid' where b_id='%s'"%(bid)
		update(q)
		q="update cycle_station set no_of_cycle=no_of_cycle-1 where s_id='%s'"%(sid)
		update(q)
		q="select * from booking inner join cycle using (c_id) inner join owner using (o_id) where b_id='%s'"%(bid)
		res=select(q)
		oid=res[0]['login_id']
		if res:
		
			q="update wallet set amount=amount+'%s' where login_id='%s'"%(amount,oid)
			update(q)


		

		data['status']='success'
	return str(data)

@api.route('/Addwallet')
def Addwallet():
	data={}
	a=request.args['amount']

	
	login_id=request.args['login_id']

	q="select * from wallet where login_id='%s'"%(login_id)
	res=select(q)
	print(q)
	if res:
		q="update wallet set amount=amount+'%s' where login_id='%s'"%(a,login_id)
		update(q)
		print(q)
			
	else:	

		q="insert into wallet values(null,'%s','%s')"%(a,login_id)
		insert(q)
	data['status']='success'
	data['method']='Addwallet'
	return str(data)

@api.route('/Viewwallet')
def Viewwallet():
	data={}
	login_id=request.args['login_id']
	q="select * from wallet where login_id='%s'"%(login_id)
	res=select(q)
	print(q)
	if res:
		data['data']=res
		data['status']='success'
	data['method']='Viewwallet'
	return str(data)

@api.route('/viewpayment')
def viewpayment():
	data={}
	login_id=request.args['login_id']
	q="select location,hours,amount,b.status AS status FROM booking b INNER JOIN rider r USING (r_id) INNER JOIN login USING (login_id) INNER JOIN cycle USING (c_id) INNER JOIN cycle_station USING (s_id) INNER JOIN OWNER o USING (o_id) WHERE b.status='Paid' OR b.status='Dropped' AND login.login_id='%s'"%(login_id)
	res=select(q)
	print(q)
	if res:
		data['data']=res
		data['status']='success'
	return str(data)


@api.route('/dropViewcyclestation')
def dropViewcyclestation():
	data={}
	q="select * from cycle_station"
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	return str(data)

@api.route('/dropcycle')
def dropcycle():
	data={}
	sid=request.args['sid']
	bid=request.args['bid']
	q="update booking set status='Dropped' where b_id='%s'"%(bid)
	update(q)
	q="update cycle set s_id='%s',status='Avaliable' where s_id='%s'"%(sid,sid)
	update(q)
	q="update cycle_station set no_of_cycle=no_of_cycle+1 where s_id='%s'"%(sid)
	update(q)
	
	data['status']='success'
	return str(data)


@api.route('/Viewcycle')
def Viewcycle():
	data={}
	sid=request.args['sid']
	q="select * from cycle inner join cycle_station using (s_id) where status='Avaliable' and s_id='%s'"%(sid)
	res=select(q)
	if res:
		data['data']=res
		data['status']='success'
	return str(data)

@api.route('/Makewallet')	
def Makewallet():
	data={}
	amt=request.args['amt']
	lid=request.args['login_id']
	bid=request.args['bid']
	sid=request.args['sid']
	q="update wallet set amount=amount-'%s' where login_id='%s'"%(amt,lid)
	update(q)
	q="update booking set status='Paid' where b_id='%s' "%(bid)
	update(q)
	q="update cycle_station set no_of_cycle=no_of_cycle-1 where s_id='%s'"%(sid)
	update(q)
	q="select * from booking inner join cycle using (c_id) inner join owner using (o_id) where b_id='%s'"%(bid)
	res=select(q)
	print(q)
	oid=res[0]['login_id']
	if res:
		q="update wallet set amount=amount+'%s' where login_id='%s'"%(amt,oid)
		update(q)
		print(q)
	data['status']='success'
	return str(data)

@api.route('/updatepasslocation')
def updatepasslocation():
	data={}
	lid=request.args['logid']
	la=request.args['latti']
	lo=request.args['longi']
	q="update rider set latitude='%s',longitude='%s' where login_id='%s' "%(la,lo,lid)
	update(q)
	data['status']='success'
	data['method']='success'
	return str(data)	


@api.route('/cancel')
def cancel():
	login_id=request.args['login_id']
	amt=request.args['amt']
	bid=request.args['bid']
	sid=request.args['sid']
	q="update booking set status='Canceled' where b_id='%s'"%(bid)
	update(q)
	q="update wallet set amount=amount+'%s' where login_id='%s'"%(amt,login_id)
	update(q)
	q="update cycle set status='Damaged' where s_id='%s'"%(sid)
	update(q)
	q="select * from booking inner join cycle using (c_id) inner join owner using (o_id) where b_id='%s'"%(bid)
	res=select(q)
	print(q)
	oid=res[0]['login_id']
	if res:
		q="update wallet set amount=amount-'%s' where login_id='%s'"%(amt,oid)
		update(q)
	data['status']='success'
	return str(data)

	



				


	
				
			
			



			

			

			
			