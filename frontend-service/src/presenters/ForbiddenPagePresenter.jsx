/*
* Is used to display a message when the user is trying to access a forbidden page.
*/
import ForbiddenPageView from "@/views/ForbiddenPageView";

/*
 * Is used to display a message when the user is trying to access a forbidden page.
 * @param {*} props The reactive model.
 * @returns A Presenter with a message
 */
export default function ForbiddenPagePresenter(props) 
{
    return <ForbiddenPageView></ForbiddenPageView>;
}