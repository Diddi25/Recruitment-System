import { RouterView } from 'vue-router'
import NavbarPresenter from './NavbarPresenter';

/**
 * Initializes the Vue app using a navbar and a router.
 * @param {*} props The reactive model.
 * @returns 
 */
export default function App(props){
    return (<div>
                <h1>Recruitment Application</h1>
                <NavbarPresenter/>
                <RouterView />
           </div>
    );
}