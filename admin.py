from flask import *
from database import*


admin=Blueprint('admin',__name__)

@admin.route('/admin_home')
def admin_home():

	return render_template('admin_home.html')


@admin.route('/admin_viewrider')
def admin_viewrider():
	data={}
	q="select * from rider inner join login using (login_id)"
	res=select(q)
	data['ride']=res

	if "action" in request.args:
		action=request.args['action']
		rid=request.args['rid']

	else:
		action=None


	if action=='accept':
		q="update login set user_type='Rider' where login_id='%s' "%(rid)
		update(q)
		return redirect(url_for('admin.admin_viewrider'))

	if action=='reject':
		q="update login set user_type='Reject' where login_id='%s'"%(rid)
		update(q)
		return redirect(url_for('admin.admin_viewrider'))

	if action=='remove':
		q="update login set user_type='rremoved' where login_id='%s'"%(rid)
		update(q)
		return redirect(url_for('admin.admin_viewrider'))

		

	return render_template('admin_viewrider.html',data=data)


@admin.route('/admin_viewowner')
def admin_viewowner():
	data={}
	q="select o.o_id,o.login_id,o.f_name,o.l_name,o.city,o.hname,o.street,l.user_type,count(co.co_id) as cmpcount from owner o inner join login l on o.login_id=l.login_id left join cycle c on c.o_id=o.o_id left join complaint co on c.c_id=o.o_id where l.user_type='owner' or l.user_type='opending' GROUP by o.o_id"
	res=select(q)
	data['own']=res

	if "action" in request.args:
		action=request.args['action']
		oid=request.args['oid']

	else:
		action=None


	if action=='accept':
		q="update login set user_type='owner' where login_id='%s' "%(oid)
		update(q)

	if action=='reject':
		q="update login set user_type='oreject' where login_id='%s'"%(oid)
		update(q)

	if action=='remove':
		q="update login set user_type='oremoved' where login_id='%s'"%(oid)
		update(q)

		

	return render_template('admin_viewowner.html',data=data)


@admin.route('/admin_viewcomplaints')
def admin_viewcomplaints():
	data={}
	q="select o.o_id as own_id,r.f_name as rname,o.f_name as oname,c.cycle_name as cname,complaint from rider r,cycle c, complaint co, owner o where r.r_id=co.r_id and c.c_id=co.c_id and c.o_id=o.o_id"
	res=select(q)
	data['complaints']=res

	if "action" in request.args:
		action=request.args['action']
		own_id=request.args['own_id']

	else:
		action=None


	if action=='view':
		admin_viewowner()

		

	return render_template('admin_viewcomplaints.html',data=data)



@admin.route('/admin_viewpaymentrider')	
def admin_viewpaymentrider():
	data={}
	q="select o_id,amount,r.f_name FROM booking b INNER JOIN rider r USING (r_id) INNER JOIN cycle USING (c_id) INNER JOIN OWNER o USING (o_id) WHERE b.status='Paid' OR b.status='Dropped'"
	res=select(q)
	data['userpay']=res

	return render_template('/admin_viewpaymentrider.html',data=data)


@admin.route('/admin_viewbooking')	
def admin_viewbooking():
	data={}
	q="select *,concat(booking.status) as bstatus from booking inner join cycle using (c_id) inner join rider using (r_id) WHERE booking.status='Paid' or booking.status='Dropped'"
	res=select(q)
	data['book']=res
	return render_template('/admin_viewbooking.html',data=data)


@admin.route('/admin_managecyclestation',methods=['post','get'])	
def admin_managecyclestation():
	data={}
	q="select * from cycle_station"
	res=select(q)
	data['cstation']=res

	if "cycle" in request.form:
		s=request.form['sname']
	
		l=request.form['location']
		q="insert into cycle_station values(null,'%s','0','%s')"%(s,l)	
		insert(q)
		return redirect(url_for('admin.admin_managecyclestation'))	


	if "action" in request.args:
		action=request.args['action']
		cid=request.args['cid']


	else:
		action=None


	if action=='update':
		q="select * from cycle_station where s_id='%s'"%(cid)
		res=select(q)
		print(q)
		data['station']=res


	if "update" in request.form:
		s=request.form['sname']
	
		l=request.form['location']

		q="update cycle_station set sname='%s' ,location='%s' where s_id='%s'"%(s,l,cid)
		update(q)
		return redirect(url_for('admin.admin_managecyclestation'))

	if action=='delete':
		q="delete from cycle_station where s_id='%s'"%(cid)
		delete(q)
		return redirect(url_for('admin.admin_managecyclestation'))
							

	return render_template('admin_managecyclestation.html',data=data)
	
			
