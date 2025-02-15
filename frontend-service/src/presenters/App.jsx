import { RouterView } from 'vue-router'
import NavbarPresenter from './NavbarPresenter';

export default function App(props){
    return (<div> <h1>Recruitment Application</h1>
        <NavbarPresenter/>
      <RouterView /></div> 
    );
}