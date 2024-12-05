import {Item} from "../models/Item.ts";
import "./ItemCard.css";
import {useNavigate} from "react-router";

type Props = {
    item: Item;
}


export default function ItemCard(props: Props) {

    const navigate = useNavigate();

    function handleClick(){
        navigate(`/item/${props.item.id}`);
    }

    return (
        <div className="itemCard-container" onClick={handleClick}>
            <div className="itemCard-tags">
                <p>{props.item.name}</p>
                <img className="item-card-img-container" src={props.item.img} alt={"image not found"}/>
            </div>
        </div>
    );
};