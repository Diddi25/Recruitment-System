import './assets/main.css'

import { createApp } from 'vue'
import App from "./presenters/App.jsx"
import router from './router/index.jsx'
import { reactive } from 'vue';
import model from './model.js'

let rModel = reactive(model);

const app = createApp(<App model={rModel}/>);

app.use(router(rModel));

app.mount('#app');
