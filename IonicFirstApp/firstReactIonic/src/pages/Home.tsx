import { IonButton, IonCol, IonContent, IonGrid, IonHeader, IonIcon, IonInput, IonItem, IonLabel, IonPage, IonRow, IonTitle, IonToolbar } from '@ionic/react';
import {calculatorOutline,refreshOutline} from 'ionicons/icons'
//var name : string
// name = "Phuong"
// name = 123 = >error

const Home: React.FC = () => {
  return (
    <IonPage>
      <IonHeader>
        <IonToolbar color="primary">
          <IonTitle>BMI Calculator</IonTitle>
        </IonToolbar>
      </IonHeader>
      <IonContent>
        <IonItem>
          <IonLabel position='floating'>Weight</IonLabel>
          <IonInput></IonInput>
        </IonItem>
        <IonItem>
          <IonLabel position='floating'>Height</IonLabel>
          <IonInput></IonInput>
        </IonItem>
        <IonGrid>
          <IonRow>
            <IonCol><IonButton>
              <IonIcon slot='start' icon={calculatorOutline}></IonIcon>
              Calculate</IonButton></IonCol>
            <IonCol><IonButton>
            <IonIcon slot='end' icon={refreshOutline}></IonIcon>
              Clear</IonButton></IonCol>
          </IonRow>
        </IonGrid>       
      </IonContent>
    </IonPage>
  );
};

export default Home;
