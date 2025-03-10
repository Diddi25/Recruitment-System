import NavbarView from "@/views/NavbarView";
import { useStore } from "vuex";

/**
 * Used to navigate the website.
 * @param {*} props The reactive model.
 * @returns A navigation bar presenter.
 */
export default function NavbarPresenter(props) {
    const store = useStore();
    return (<div class="main">
                <NavbarView model={props.model}
                            store={store} onLogout={logout}
                />
           </div>);

    function logout() {
        //console.log(props);
    }
}