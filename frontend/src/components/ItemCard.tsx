import {Item} from "../models/Item.ts";
import "./ItemCard.css";

type Props = {
    item: Item;
}


export default function ItemCard(props: Props) {
    return (
        <div className="itemCard-container">
            <img src={""} alt="item image"/>
            <div className="itemCard-tags">
                <p>{props.item.name}</p>
                <p>{props.item.category}</p>
                <p>{props.item.status}</p>
            </div>
        </div>
    );
};