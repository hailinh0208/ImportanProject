import type { Preview } from '@storybook/react';

const preview: Preview = {
    parameters: {
        layout: 'fullscreen',
        backgrounds: {
            default: 'light',
            values: [
                { name: 'light', value: '#f0f0f0' },
                { name: 'dark', value: '#1a1a1a' },
            ],
        },
    },
};

export default preview;
