import NavbarView from "@/views/NavbarView";
import { useStore } from "vuex";

/**
 * Used to navigate the website.
 * @param {*} props The reactive model.
 * @returns 
 */
export default function NavbarPresenter(props) {
    const store = useStore();
    return (<div class="main">
                <NavbarView model={props.model}
                            store={store}
                />
           </div>);
}