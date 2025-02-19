import ApplicationListView from "@/views/ApplicationListView";

export default function ApplicationListPresenter(props) {
    return <div class="main"><ApplicationListView model={props.model} viewApplicant={onViewApplicant}/></div>;

    function onViewApplicant(){

    }
}