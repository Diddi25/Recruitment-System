import NavbarView from "@/views/NavbarView";
export default function NavbarPresenter(props) {
    return (<div class="main"><NavbarView model={props.model}/></div>);
}