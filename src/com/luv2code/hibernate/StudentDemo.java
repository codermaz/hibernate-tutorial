package com.luv2code.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.luv2code.hibernate.demo.entity.Student;

public class StudentDemo {

	public static void main(String[] args) {
		
		// create session factory
		SessionFactory factory = new Configuration()
								.configure("hibernate.cfg.xml")
								.addAnnotatedClass(Student.class)
								.buildSessionFactory();
		
		// create session
		Session session = factory.getCurrentSession();
		
		Student tempStudent;
		try{
			// use the session object to save Java object
			
			// create a student object
			tempStudent= new Student("Ali2", "Yildirim", "aliyildirim@luv2code.com");
			
			// start a transaction
			session.beginTransaction();
			
			// save the student object
			session.save(tempStudent);
			
			// commit the transaction
			session.getTransaction().commit();
			

			// find out the student's id : primary key
			System.out.println("student saved.Generated id : " + tempStudent.getId());
			
			// --- READ
			
			// get a new session and start transaction
			session = factory.getCurrentSession();
			session.beginTransaction();	
			
			// retrieve student based on the id: primary key
			System.out.println("Getting student with id: " + tempStudent.getId());
			Student myStudent= session.get(Student.class, tempStudent.getId());
			System.out.println("Get complete : "+ myStudent);
		
			
			// --- QUERY
			
			// query students
			@SuppressWarnings("unchecked") // his warning appear always when I create new query: Type safety: The expression of type List needs unchecked conversion to conform to List<Student>
			List<Student> theStudents = session.createQuery("from Student").getResultList(); // List>> import from java.util
					
			// display students		
			displayStudents(theStudents);
			
			// query students lastname= "yildirim"
			theStudents= session.createQuery("from Student s where s.lastName='Yildirim'").getResultList();
			System.out.println("\n\nStudents with the lastname 'Yildirim'");
			displayStudents(theStudents);
			
			// query students lastname='Yilmaz' or 'Sanirim'
			theStudents= session.createQuery("from Student s where "
								+"s.lastName='Sanirim' or s.lastName='Yilmaz'").getResultList();
			System.out.println("\n\nStudents with the lastname 'Yilmaz and Sanirim'");
			displayStudents(theStudents);
			
			// query students emails like "luv2code.com"
			theStudents= session.createQuery("from Student s where "
					+"s.email LIKE '%luv2code.com' ")
					.getResultList();
			System.out.println("\n\nStudents whose emails end with luv2code");
			displayStudents(theStudents);
		
			
			// ---  UPDATE
			int studentId=1;
			Student myUStudent = session.get(Student.class, studentId);
			myUStudent.setFirstName("updatedName");
			
			// update email for all students --> Another way for updating
			//			System.out.println("Update email for all students");
			//			session.createQuery("update Student set email='foo@gmail.com'")
			//					.executeUpdate();
			
			
			// --- DELETE 
			//			int studentDId=1;
			//			Student myDStudent = session.get(Student.class, studentDId);
			//			session.delete(myDStudent);
		
			// delete student id = 2 --> Another way for updating
			//			session.createQuery("delete from Student where id=2")
			//					.executeUpdate();

			// commit the transaction 
			session.getTransaction().commit();
			
			/*
				 @NamedQueries({
					@NamedQuery(
					name = "findStockByStockCode",
					query = "from Stock s where s.stockCode = :stockCode"
					)
				})
				@Entity
				@Table(name = "stock", catalog = "mkyong")
				public class Stock implements java.io.Serializable {
				... }
				...
				Query query = session.getNamedQuery("findStockByStockCode")
							  .setString("stockCode", "7277");		
				
			*/
			
			System.out.println("Alles in Ordnung...");
		}
		finally {
			factory.close();
		}
		
	}

	private static void displayStudents(List<Student> theStudents) {
		for(Student tempQStudent : theStudents){
			System.out.println(tempQStudent);
		}
	}
	
}
