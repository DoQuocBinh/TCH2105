const btnCalculate = document.getElementById('button-calculate')
const btnReset = document.getElementById('button-clear')
const inputWeight = document.getElementById('input-weight')
const inputHeight = document.getElementById('input-height')
const resultDiv = document.getElementById('result')

const calculateHandler = ()=>{
    const weight = inputWeight.value
    const height = inputHeight.value
    const bmi= weight / (height*height)
    
    const ionCard = document.createElement('ion-card')
    ionCard.innerHTML = `
        <ion-card-header>
            <ion-card-title>Your BMI</ion-card-title>
        </ion-card-header>
        <ion-card-content class="ion-text-center">
            ${bmi.toFixed(2)}
        </ion-card-content>
    `
    resultDiv.innerHTML = ''
    resultDiv.appendChild(ionCard)

}

btnCalculate.addEventListener('click',calculateHandler)