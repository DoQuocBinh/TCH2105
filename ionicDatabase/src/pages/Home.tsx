import { IonButton, IonContent, IonHeader, IonImg, IonInput, IonItem, IonLabel, IonList, IonPage, IonSelect, IonSelectOption, IonThumbnail, IonTitle, IonToolbar } from '@ionic/react';
import { useEffect, useState } from 'react';
import ExploreContainer from '../components/ExploreContainer';
import { getAllCustomer, insertCustomer } from '../databaseHandler';
import { Customer } from '../models/Customer';
import './Home.css';

const Home: React.FC = () => {
  const [name, setName] = useState('')
  const [languages,setLanguages] = useState<string[]>([])
  const [picture,setPicture] = useState('')
  const [allCustomers, setAllCustomers] = useState<Customer[]>([])

  const fetchData = async ()=>{
    const result = await getAllCustomer()
    setAllCustomers(result)
  }

  useEffect(()=>{
    fetchData()
  })

  const saveHandler = async ()=>{
    const newCus : Customer = {name:name,languages:languages,picture:picture}
    const id =  await insertCustomer(newCus)
    alert(id)
  }
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="secondary">
          <IonTitle>Customer Management</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonItem>
          <IonLabel>Name</IonLabel>
          <IonInput onIonChange={e=>setName(e.detail.value!)}></IonInput>
        </IonItem>
        <IonItem>
          <IonLabel>Nationality</IonLabel>
          <IonSelect multiple onIonChange={e=>setLanguages(e.detail.value!)}>
            <IonSelectOption value="Vietnames">Vietnames</IonSelectOption>
            <IonSelectOption value="spainish">Spainish</IonSelectOption>
            <IonSelectOption value="British">British</IonSelectOption>
          </IonSelect>
        </IonItem>
        <IonItem>
          <IonLabel>Picture</IonLabel>
          <IonInput onIonChange={e=>setPicture(e.detail.value!)}></IonInput>
        </IonItem>
        <IonButton onClick={saveHandler} expand='block' class='ion-margin'>Save</IonButton>
      {allCustomers &&
      <IonList>
        {allCustomers.map(c=>
          <IonItem>
            <IonThumbnail slot='end'>
              <IonImg src={c.picture}></IonImg>
            </IonThumbnail>
            {c.name}
          </IonItem>
          )}
      </IonList>

      }
      </IonContent>
    </IonPage>
  );
};

export default Home;
