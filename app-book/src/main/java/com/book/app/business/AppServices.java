package com.book.app.business;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import entities.Collection;
import entities.Image;
import entities.Item;
import entities.User;

@Stateless
@SuppressWarnings("unchecked")
public class AppServices implements InfAppServices  {

	@PersistenceContext(unitName = "persistence-unit" )
    private EntityManager entityManager;
	
	
	@Override
	public void signUpUser(@NotNull User user) {

		 if(user==null || user.getEmail()==null){
			 throw new IllegalArgumentException("> "
			 		+ "El parametro User no debe ser null y debe tener un email valido "); 
		 }
		
		 //TODO verificar formato correo 

		List<User> list = entityManager.createNamedQuery(User.QUERY_USER_BY_EMAIL) 
    			.setParameter("email",user.getEmail()).getResultList(); 
		
		if(list!=null && list.size()>0){
			throw new EntityExistsException("El User tiene un email que esta registrado"); 
		}
		
		entityManager.persist(user); 
	}
	

	@Override
	public User signInUser(@NotNull String email) { 
		
		List<User> list = entityManager.createNamedQuery(User.QUERY_USER_BY_EMAIL) 
    			.setParameter("email",email).getResultList(); 
		 
		//TODO verificar formato correo 
		
		if(list==null || list.size()!=1){
			throw new EntityNotFoundException(""
					+ "No se encuentra un usuario con el email: " + email); 
		}
		
		return list.get(0); 		
	}

	@Override
	public void signOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCollection(@NotNull String userId, 
								@NotNull Collection collection) {
		
		User user = entityManager.find(User.class, userId); 
		if(user==null){
			throw new EntityNotFoundException(""
					+ "No se encuentra un usuario con el userId: " + userId); 
		}
		
		entityManager.persist(collection); 
		collection.setUser(user); 
		user.getCollections().add(collection);
		
	}

	@Override
	public void removeCollection(String collectionId) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void addImage(String itemId, Image image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateItem(Item item, byte[] bytes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeItem(String itemId) {
		// TODO Auto-generated method stub
		
	}
	

	@Override
	public <T> T find(Class<T> clazz, Object id) {		
		return entityManager.find(clazz, id); 
	}
	
	@Override
	public  void  remove(Object entity) {		
		entityManager.remove(entity); 
	}


	@Override
	public <T> List<T> getAll(Class<T> clazz) {
		String clasName = clazz.getSimpleName(); 
		return entityManager.createQuery("SELECT o FROM " + clasName  + " o")
				.getResultList(); 	
	}


	@Override
	public <T> void deleteAll(Class<T> clazz) {
		String clasName = clazz.getSimpleName(); 
		entityManager.createQuery("DELETE FROM " +clasName).executeUpdate();
	}


	@Override
	public void addItem(String collectionId, Item item, byte[] bytes) {
		// TODO Auto-generated method stub
		
	}
	


}
