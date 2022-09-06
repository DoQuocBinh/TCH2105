import {openDB} from 'idb'
import { Customer } from './models/Customer';

const DATABASE_NAME = "CustomerDB"

export async function insertCustomer(customerInfo:Customer){
    const db = await openDB(DATABASE_NAME,1)
    const id = await db.put("customers",customerInfo)
    return id
}

export async function getAllCustomer(){
    const db = await openDB(DATABASE_NAME,1)
    return await db.getAll("customers")
}

initDatabase().then(()=>{
    console.log("database was created!")
})

async function initDatabase() {
    const db = await openDB(DATABASE_NAME, 1, {
      upgrade(db) {
        // Create a store of objects -> table customers
        const store = db.createObjectStore('customers', {
          // The 'id' property of the object will be the key.
          keyPath: 'id', //->primary key column
          // If it isn't explicitly set, create a value by auto incrementing.
          autoIncrement: true,  //->dont neet to set the value for PK
        });      
      },
    });
}