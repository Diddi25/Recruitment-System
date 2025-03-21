import './assets/main.css';
import { createApp } from 'vue';
import App from "./presenters/App.jsx";
import router from './router/index.jsx';
import store from './store/storeIndex.js';
import { reactive } from 'vue';
import model from './model.js';


/*
Initializes the Vue app, store and model. 
*/
let rModel = reactive(model);
const app = createApp(<App model={rModel}/>);
app.use(router(rModel));
app.use(store);
app.mount('#app');