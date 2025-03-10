import NavbarView from "@/views/NavbarView";

/**
 * Used to navigate the website.
 * @param {*} props The reactive model.
 * @returns A navigation bar presenter.
 */
export default function NavbarPresenter(props) {
    return (<div class="main">
                <NavbarView isLoggedIn = {props.model.user.isLoggedIn} role={props.model.user.role} logoutACB={logout}
                />
           </div>);

    function logout() {
        props.model.logoutUser();
    }
}