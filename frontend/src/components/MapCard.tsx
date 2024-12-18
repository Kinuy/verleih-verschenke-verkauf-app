import "./MapCard.css"
import "leaflet/dist/leaflet.css";
import {MapContainer, Marker, Popup, TileLayer} from "react-leaflet";
import {Icon} from "leaflet";
import {Item} from "../models/Item.ts";

const customIcon = new Icon({
    iconUrl: "https://cdn-icons-png.flaticon.com/128/2776/2776067.png",
    iconSize: [38, 38] // size of the icon
});

type Props = {
    item: Item;
}

export default function MapCard(props:Props) {
    return (
        <MapContainer center={[52.506615, 13.317524]} zoom={5}>
            <TileLayer
                attribution=""
                url="https://tile.openstreetmap.org/{z}/{x}/{y}.png"
            />
            <Marker position={props.item.geocode} icon={customIcon}>
                <Popup>{props.item.name}</Popup>
            </Marker>
        </MapContainer>


    );
}