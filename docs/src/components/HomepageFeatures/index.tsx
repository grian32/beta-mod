import type {ReactNode} from 'react';
import clsx from 'clsx';
import Heading from '@theme/Heading';
import styles from './styles.module.css';

type FeatureItem = {
    title: string;
    image?: string;
    description: ReactNode;
};

const FeatureList: FeatureItem[] = [

    {
        title: 'Item Enhancements',
        image: require('@site/static/img/enhancement_table_main.png').default,
        description: (
            <>
                Uprade your tools with powerful effects at the Enhancement Table.
            </>
        ),
    },
    {
        title: 'Icy Dungeons',
        image: require('@site/static/img/icy_dungeon.png').default,
        description: (
            <>
                Explore rare underground dungeons hidden beneath Taiga and Tundra biomes
            </>
        ),
    },
    {
        title: 'Coming Soon',
        description: (
            <>
            </>
        ),
    },
    // {
    //     title: 'Mysterious Shrine',
    //     Svg: require('@site/static/img/icy_dungeon.png').default,
    //     description: (
    //         <>
    //             A shrine that rarely pops up across the world, it is known that it can be activated, although the result is not.
    //         </>
    //     ),
    // },
];

function Feature({title, image, description}: FeatureItem) {
    return (
        <div className={clsx('col col--4')}>
            <div className="text--center">
                {image ? (
                    <img className={styles.featureImage} src={image} alt={title}/>
                ) : (
                    <div className={clsx(styles.featureImage, styles.comingSoonImage)}>
                        Coming Soon
                    </div>
                )}
            </div>
            <div className="text--center padding-horiz--md">
                <Heading as="h3">{title}</Heading>
                <p>{description}</p>
            </div>
        </div>
    );
}

export default function HomepageFeatures(): ReactNode {
    return (
        <section className={styles.features}>
            <div className={clsx('container', styles.featureContainer)}>
                <div className="row">
                    {FeatureList.map((props, idx) => (
                        <Feature key={idx} {...props} />
                    ))}
                </div>
            </div>
        </section>
    );
}
