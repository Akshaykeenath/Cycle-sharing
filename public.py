from flask import * 
from database import*


public=Blueprint('public',__name__)

@public.route('/')
def home():
	return render_template('home.html')


@public.route('/owner_registration',methods=['post','get'])	
def owner_registration():
	if "owner" in request.form:
		s=request.form['fname']
		p=request.form['lname']
		l=request.form['city']
		ph=request.form['add']
		e=request.form['street']
		u=request.form['uname']
		pa=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:

			flash('Username already exist')

		else:
			
			q="insert into login values(null,'%s','%s','opending')"%(u,pa)
			id=insert(q)
			q="insert into owner values(null,'%s','%s','%s','%s','%s','%s')"%(id,s,p,l,ph,e)
			insert(q)
			q="insert into wallet values(null,'0','%s')"%(id)
			insert(q)
			flash('successfully')
			return redirect(url_for('public.owner_registration'))

	return render_template('owner_registration.html')


@public.route('/login',methods=['post','get'])	
def login():
	if "login" in request.form:
		u=request.form['uname']
		pa=request.form['pwd']
		q="select * from login where username='%s' and password='%s'"%(u,pa)
		res=select(q)
		if res:
			session['login_id']=res[0]['login_id']
			lid=session['login_id']
		if res[0]['user_type']=="admin":
			return redirect(url_for('admin.admin_home'))
		elif res[0]['user_type']=="owner":
			q="select * from owner where login_id='%s'"%(lid)
			res=select(q)
			if res:
				session['o_id']=res[0]['o_id']
				oid=session['o_id']
				return redirect(url_for('owner.owner_home'))


		else:
			flash ('invalid username and password')

		

	return render_template('login.html')	

@public.route('/changepassword',methods=['post','get'])
def changepassword():
	if "login" in request.form:
		uname=request.form['uname']
		pwd=request.form['pwd']
		pwd2=request.form['pwd2']
		if pwd==pwd2:
			q="update login set password='%s' where username='%s'"%(pwd,uname)
			update(q)
			flash('successfully updated')
			flash(uname)
		else:
			flash('Enter again')
	return render_template('changepassword.html')