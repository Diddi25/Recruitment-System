import { RouterView } from 'vue-router'
import NavbarPresenter from './NavbarPresenter';

/**
 * Initializes the Vue app using a navbar and a router.
 * @param {*} props The reactive model.
 * @returns The web page content.
 */
export default function App(model){
    return (<div>
                <h1>Recruitment Application</h1>
                <NavbarPresenter model={model}/>
                <RouterView />
           </div>
    );
}