/*
* The navigation bar is used to switch between different router pages.
*/
import NavbarView from "@/views/NavbarView";
import { useStore } from "vuex";

export default function NavbarPresenter(props) {
    const store = useStore();
    return (<div class="main">
                <NavbarView model={props.model}
                            store={store}
                />
           </div>);
}